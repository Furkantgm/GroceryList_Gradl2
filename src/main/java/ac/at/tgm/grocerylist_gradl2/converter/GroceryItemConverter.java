package ac.at.tgm.grocerylist_gradl2.converter;

import ac.at.tgm.grocerylist_gradl2.dto.GroceryItemDto;
import ac.at.tgm.grocerylist_gradl2.entity.GroceryItemEntity;
import org.springframework.stereotype.Component;

@Component
public class GroceryItemConverter {

    /**
     * Wandelt eine JPA-Entity in ein DTO um.
     * @param entity die Datenbank-Entity, kann null sein
     * @return das DTO mit gleichen Feldwerten oder null, falls input null
     */
    public GroceryItemDto entityToDto(GroceryItemEntity entity) {
        // Null-Check: wenn keine Entity übergeben wurde, nichts konvertieren
        if (entity == null) return null;

        // Neues DTO befüllen und zurückgeben
        return new GroceryItemDto(
                entity.getId(),        // Datenbank-ID
                entity.getName(),      // Produktname
                entity.getAmount(),    // Menge
                entity.getCollected()  // Erfasst-Status
        );
    }

    /**
     * Wandelt ein DTO in eine JPA-Entity um.
     * @param dto das Datenobjekt, kann null sein
     * @return die Entity mit gleichen Feldwerten oder null, falls input null
     */
    public GroceryItemEntity dtoToEntity(GroceryItemDto dto) {
        // Null-Check: wenn kein DTO übergeben wurde, nichts konvertieren
        if (dto == null) return null;

        // Neue Entity anlegen und Felder setzen
        GroceryItemEntity entity = new GroceryItemEntity();
        entity.setId(dto.getId());             // ID übernehmen (kann null sein, dann wird eine neue erstellt)
        entity.setName(dto.getName());         // Produktname
        entity.setAmount(dto.getAmount());     // Menge
        entity.setCollected(dto.getCollected());// Erfasst-Status
        return entity;
    }
}
