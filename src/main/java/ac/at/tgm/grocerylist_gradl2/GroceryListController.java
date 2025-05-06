package ac.at.tgm.grocerylist_gradl2;

import ac.at.tgm.grocerylist_gradl2.dto.GroceryItemDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * REST-Controller für die Verwaltung der Einkaufsliste.
 * @author Furkan
 * @version 2025-04-30
 */
@RestController
@RequestMapping("/api/grocery")
public class GroceryListController {

    private final GroceryListService service;

    /**
     * Konstruktor zur Dependency Injection des Services.
     * @param service die Geschäftslogik
     */
    @Autowired
    public GroceryListController(GroceryListService service) {
        this.service = service;
    }

    /**
     * Produkt hinzufügen (POST)
     * Erwartet ein JSON-Objekt ohne ID. Setzt Standardwerte für amount (1) und verarbeitet collected.
     * @param dto das übermittelte Produkt (ohne ID)
     * @return das neu erstellte Produkt mit gesetzter ID und HTTP 201
     */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid GroceryItemDto dto) {
        if (dto.getId() != null) {
            return ResponseEntity.badRequest().body("Id muss null sein");
        }

        if (dto.getAmount() == null) {
            dto.setAmount(1);
        }

        // Neues DTO wird vorbereitet
        GroceryItemDto mapped = new GroceryItemDto();
        mapped.setName(dto.getName());
        mapped.setAmount(dto.getAmount());
        mapped.setCollected(dto.isCollected());
        mapped.setId(null); // ID wird in Service Klasse vergeben

        GroceryItemDto created = service.createGroceryItem(new GroceryItemDto(
                null,
                mapped.getName(),
                mapped.getAmount(),
                mapped.isCollected()
        ));

        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    /**
     * Erwartet ein vollständiges Produkt mit gesetzter ID. Setzt Standardwerte und überprüft Name.
     * @param dto das zu aktualisierende Produkt
     * @return das aktualisierte Produkt oder 404, falls nicht gefunden
     */
    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Valid GroceryItemDto dto) {
        if (dto.getId() == null) {
            return ResponseEntity.badRequest().body("Id darf nicht null sein");
        }

        if (dto.getName() != null) {
            dto.setName(dto.getName().trim());
        }

        if (dto.getAmount() == null) {
            dto.setAmount(1);
        }
        if(dto.getAmount() < 0){
            int i = dto.getAmount() * (-1);
            dto.setAmount(i);
        }

        if (dto.getCollected() == null) {
            dto.setCollected(false);
        }

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

    /**
     * @param id Produkt-ID
     * @param collected neuer Wert für das Feld
     * @return aktualisiertes Produkt oder 404 mit Fehlermeldung
     */
    @PatchMapping("/{id}")
    public ResponseEntity<?> patch(@PathVariable Long id, @RequestParam boolean collected) {
        try {
            GroceryItemDto patched = service.patchGroceryItem(id, collected);
            return ResponseEntity.ok(patched);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id existiert nicht");
        }
    }

    /**
     * @return Liste aller Produkte
     */
    @GetMapping
    public List<GroceryItemDto> getAll() {
        return service.getGroceryItems();
    }

    /**
     * Gibt 404 zurück, wenn Produkt nicht existiert.
     * @param id Produkt-ID
     * @return Produkt oder 404
     */
    @GetMapping("/{id}")
    public ResponseEntity<GroceryItemDto> getOne(@PathVariable Long id) {
        Optional<GroceryItemDto> found = service.getGroceryItem(id);
        return found.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Wenn keine ID übergeben wird, werden alle gelöscht.
     * @param id optional, Produkt-ID
     * @return HTTP 200 oder 404, wenn ID ungültig
     */
    @DeleteMapping({"","/{id}"})
    public ResponseEntity<Void> deleteAllOrOne(@PathVariable(value="id",required = false) Long id) {
        try {
            service.deleteGroceryItem(id);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
