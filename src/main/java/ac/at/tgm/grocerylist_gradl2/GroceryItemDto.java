package ac.at.tgm.grocerylist_gradl2;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class GroceryItemDto {

    private Long id;

    @NotBlank(message = "Name darf nicht leer sein")
    @Size(max = 255, message = "name größe muss zwischen 0 und 255 sein")
    private String name;

    // Optionaler Wert – keine Validation!
    private Integer amount;

    private Boolean collected;

    public GroceryItemDto() {
    }

    public GroceryItemDto(Long id, String name, Integer amount, Boolean collected) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.collected = collected;
    }

    // Getter & Setter
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
