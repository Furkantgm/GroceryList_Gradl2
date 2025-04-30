package ac.at.tgm.grocerylist_gradl2;



import java.util.List;
import java.util.Optional;

/**
 * Geschäftslogik zur Verwaltung der Produkte einer Einkaufsliste
 *
 * @author Michael Pointner
 * @version 2025-02-04
 */
public interface GroceryListService {
    
    /**
     * A: Produkt zur Einkaufsliste hinzufügen
     *
     * @param groceryItemDto Zu erstellendes Produkt (id == null)
     * @return Erstelltes Produkt mit gesetzter eindeutiger id != null
     */
    GroceryItemDto createGroceryItem(GroceryItemDto groceryItemDto);
    
    /**
     * B: Produkt der Einkaufsliste aktualisieren
     *
     * @param groceryItemDto Zu aktualisierendes Produkt (id != null)
     * @return Aktualisiertes Produkt (d.h. Rückgabewert der Datenbank)
     */
    GroceryItemDto updateGroceryItem(GroceryItemDto groceryItemDto);
    
    /**
     * C: Produkt zum Teil aktualisieren
     *
     * @param id        Id des Produktes
     * @param collected Zu setzender collected-Wert
     * @return Gesamtes aus der Datenbank geladenes Dto inklusive aktualisiertem collected-Wert
     */
    GroceryItemDto patchGroceryItem(Long id, boolean collected);
    
    /**
     * D: Liste aller Produkte der Einkaufsliste
     *
     * @return Alle gespeicherten Produkte
     */
    List<GroceryItemDto> getGroceryItems();
    
    /**
     * E: Produkt der Einkaufsliste mit angegebene Id erhalten
     *
     * @param id Id des gewünschten Produktes
     * @return Das gewünschte Produkt
     */
    Optional<GroceryItemDto> getGroceryItem(Long id);
    
    /**
     * F: Produkt mit der übergebenen Id oder alle löschen
     *
     * @param id Id des zu löschenden Produktes oder null für alle Produkte
     */
    void deleteGroceryItem(Long id);
}
