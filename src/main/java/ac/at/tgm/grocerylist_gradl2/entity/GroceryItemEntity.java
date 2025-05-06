package ac.at.tgm.grocerylist_gradl2.entity;

import jakarta.persistence.*;

/**
 * Repräsentiert ein Element in der Einkaufsliste als JPA-Entity.
 * Diese Klasse wird von Hibernate automatisch in die Tabelle GROCERY_ITEM gemappt.
 */
@Entity
@Table(name = "GROCERY_ITEM")  // Gibt explizit den Tabellennamen vor
public class GroceryItemEntity {

    /** Primärschlüssel der Tabelle, wird automatisch generiert. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Name des Produkts, darf nicht null sein, max. Länge 255 Zeichen. */
    @Column(nullable = false, length = 255)
    private String name;

    /** Menge des Produkts, darf nicht null sein. */
    @Column(nullable = false)
    private Integer amount;

    /** Ob das Produkt bereits gesammelt wurde, darf nicht null sein. */
    @Column(nullable = false)
    private Boolean collected;

    /**
     * Standard-Konstruktor ohne Argumente.
     * Wird von JPA benötigt, um Entities instanziieren zu können.
     */
    public GroceryItemEntity() {}

    /**
     * Hilfskonstruktor, um eine Entity manuell anzulegen (ohne ID).
     * @param name      Produktname
     * @param amount    Menge
     * @param collected Sammelstatus
     */
    public GroceryItemEntity(String name, Integer amount, Boolean collected) {
        this.name = name;
        this.amount = amount;
        this.collected = collected;
    }

    // --- Getter und Setter ---

    /** @return die automatisch vergebene ID */
    public Long getId() {
        return id;
    }

    /** @param id legt die ID manuell fest (meistens null lassen für Auto-Generation) */
    public void setId(Long id) {
        this.id = id;
    }

    /** @return Name des Produkts */
    public String getName() {
        return name;
    }

    /** @param name setzt den Produktnamen */
    public void setName(String name) {
        this.name = name;
    }

    /** @return Menge des Produkts */
    public Integer getAmount() {
        return amount;
    }

    /** @param amount setzt die Menge */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /** @return true, wenn das Produkt gesammelt ist, sonst false */
    public Boolean getCollected() {
        return collected;
    }

    /** @param collected setzt den Sammelstatus */
    public void setCollected(Boolean collected) {
        this.collected = collected;
    }
}
