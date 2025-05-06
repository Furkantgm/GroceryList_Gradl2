package ac.at.tgm.grocerylist_gradl2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object (DTO) für ein Produkt in der Einkaufsliste.
 * Wird verwendet, um Daten zwischen Controller und Service zu transportieren.
 * Beinhaltet Validierung für den Namen des Produkts.
 *
 * @author Furkan
 * @version 2025-04-30
 */
public class GroceryItemDto {
    private Long id;
    @NotBlank(message = "Name darf nicht leer sein")
    @Size(max = 255, message = "name größe muss zwischen 0 und 255 sein")
    private String name;
    private Integer amount;
    private Boolean collected;
    public GroceryItemDto() {
    }

    /**
     * Konstruktor zum manuellen Erstellen eines Objekts mit allen Feldern.
     *
     * @param id        ID des Produkts
     * @param name      Name des Produkts
     * @param amount    Menge
     * @param collected Ob das Produkt eingesammelt wurde
     */
    public GroceryItemDto(Long id, String name, Integer amount, Boolean collected) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.collected = collected;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Boolean isCollected() {
        return collected;
    }

    public void setCollected(Boolean collected) {
        this.collected = collected;
    }

    public Boolean getCollected() {
        return collected;
    }
}
