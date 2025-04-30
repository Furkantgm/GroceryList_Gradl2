package ac.at.tgm.grocerylist_gradl2;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/grocery")
public class GroceryListController {

    private final GroceryListService service;

    public GroceryListController(GroceryListService service) {
        this.service = service;
    }

    // A: Produkt hinzufügen

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid GroceryItemDto dto) {
        if (dto.getId() != null) {
            return ResponseEntity.badRequest().body("Id muss null sein");
        }

        // amount prüfen und Standardwert setzen
        if (dto.getAmount() == null) {
            dto.setAmount(1);
        }


        GroceryItemDto mapped = new GroceryItemDto();
        mapped.setName(dto.getName());
        mapped.setAmount(dto.getAmount());
        mapped.setCollected(dto.isCollected());
        mapped.setId(null); // wird vom Service gesetzt


        GroceryItemDto created = service.createGroceryItem(new GroceryItemDto(
                null,
                mapped.getName(),
                mapped.getAmount(),
                mapped.isCollected()
        ));

        return new ResponseEntity<>(created, HttpStatus.CREATED); // 201
    }


    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Valid GroceryItemDto dto) {
        if (dto.getId() == null) {
            return ResponseEntity.badRequest().body("Id darf nicht null sein");
        }

        if (dto.getName() != null) {
            dto.setName(dto.getName().trim());
        }

        if (dto.getName() == null || dto.getName().isBlank()) {
            return ResponseEntity.badRequest().body("Name darf nicht leer sein");
        }

        if (dto.getAmount() == null) {
            dto.setAmount(1);
        }

        if (dto.getCollected() == null) {
            dto.setCollected(false);
        }

        // Neues Objekt erzeugen für Übergabe
        GroceryItemDto mapped = new GroceryItemDto(
                dto.getId(),
                dto.getName(),
                dto.getAmount(),
                dto.getCollected()
        );

        try {
            GroceryItemDto updated = service.updateGroceryItem(mapped);
            return ResponseEntity.ok(updated);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }




    @PatchMapping("/{id}")
    public ResponseEntity<?> patch(@PathVariable Long id, @RequestParam boolean collected) {
        try {
            GroceryItemDto patched = service.patchGroceryItem(id, collected);
            return ResponseEntity.ok(patched);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id existiert nicht");
        }
    }



    // D: Alle Produkte anzeigen
    @GetMapping
    public List<GroceryItemDto> getAll() {
        return service.getGroceryItems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroceryItemDto> getOne(@PathVariable Long id) {
        Optional<GroceryItemDto> found = service.getGroceryItem(id);
        return found.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build()); // <-- 404 statt 400
    }

    // F: Produkt löschen oder alle löschen
    @DeleteMapping
    public ResponseEntity<Void> deleteAllOrOne(@RequestParam(required = false) Long id) {
        try {
            service.deleteGroceryItem(id);
            return ResponseEntity.ok().build(); // <-- statt 204
        } catch (NoSuchElementException e) {

            return ResponseEntity.notFound().build(); // 404
        }
    }

}
