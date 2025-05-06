package ac.at.tgm.grocerylist_gradl2.service;

import ac.at.tgm.grocerylist_gradl2.dto.GroceryItemDto;
import ac.at.tgm.grocerylist_gradl2.GroceryListService;
import ac.at.tgm.grocerylist_gradl2.entity.GroceryItemEntity;
import ac.at.tgm.grocerylist_gradl2.repository.GroceryItemRepository;
import ac.at.tgm.grocerylist_gradl2.converter.GroceryItemConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Profile("default")         // Lädt diese Implementierung nur im "default"-Profil (echte DB)
@Transactional               // Öffnet eine Transaktion für alle Methoden, schließt sie nach Rückkehr
public class GroceryListServiceImpl implements GroceryListService {

    @Autowired
    private GroceryItemRepository repository;    // JPA-Repository für Entity-Zugriff

    @Autowired
    private GroceryItemConverter converter;      // Wandelt zwischen DTO und Entity

    @Override
    public GroceryItemDto createGroceryItem(GroceryItemDto dto) {
        // 1. DTO → Entity: bereite Datenbank-Entität vor
        GroceryItemEntity entity = converter.dtoToEntity(dto);
        // 2. Speichern in DB: save() gibt die gespeicherte Entity (mit ID) zurück
        GroceryItemEntity saved = repository.save(entity);
        // 3. Entity → DTO: sende nur DTO zurück, nicht die Entity selbst
        return converter.entityToDto(saved);
    }

    @Override
    public GroceryItemDto updateGroceryItem(GroceryItemDto dto) {
        // Lade existierende Entity; wirft NoSuchElementException, wenn nicht vorhanden
        GroceryItemEntity entity = repository.findById(dto.getId()).orElseThrow();
        // Aktualisiere Felder auf Basis des DTO
        entity.setName(dto.getName());
        entity.setAmount(dto.getAmount());
        entity.setCollected(dto.getCollected());
        // Durch @Transactional wird die Änderung am Ende automatisch gespeichert
        return converter.entityToDto(entity);
    }

    @Override
    public GroceryItemDto patchGroceryItem(Long id, boolean collected) {
        // Teil-Update: nur das "collected"-Feld ändern
        GroceryItemEntity entity = repository.findById(id).orElseThrow();
        entity.setCollected(collected);
        return converter.entityToDto(entity);
    }

    @Override
    public List<GroceryItemDto> getGroceryItems() {
        // Hole alle Entities, konvertiere jede zu DTO und sammle in Liste
        return repository.findAll().stream()
                .map(converter::entityToDto)
                .toList();
    }

    @Override
    public Optional<GroceryItemDto> getGroceryItem(Long id) {
        // Lade Entity optional (kann leer sein) und mappe sie zu DTO
        return repository.findById(id)
                .map(converter::entityToDto);
    }

    @Override
    public void deleteGroceryItem(Long id) {
        // id == null → lösche alle; sonst lösche spezifische Entity
        if (id == null)
            repository.deleteAll();
        else
            repository.deleteById(id);
    }
}
