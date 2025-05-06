package ac.at.tgm.grocerylist_gradl2;


import ac.at.tgm.grocerylist_gradl2.dto.GroceryItemDto;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Geschäftslogik zur Verwaltung der Produkte einer Einkaufsliste mit gefakter Datenbankspeicherung
 *
 * @author Michael Pointner
 * @version 2025-02-04
 */
@Service
@Profile("test")
@Scope("singleton")
public class GroceryListServiceMock implements GroceryListService {
    private final List<GroceryItemDto> list = new ArrayList<>();
    private Long counter = 0L;

    GroceryListServiceMock() {
        list.add(new GroceryItemDto(++counter, "Apfel", 6, false));
        list.add(new GroceryItemDto(++counter, "Weckerl", 3, false));
        list.add(new GroceryItemDto(++counter, "Milch", 2, false));
        list.add(new GroceryItemDto(++counter, "Joghurt", 2, false));
        list.add(new GroceryItemDto(++counter, "Karotten", 4, false));
        list.add(new GroceryItemDto(++counter, "Zucchini", 2, false));
        list.add(new GroceryItemDto(++counter, "Käse", 1, false));
        list.add(new GroceryItemDto(++counter, "Müsli", 1, false));
        list.add(new GroceryItemDto(++counter, "Mehl", 1, false));
        list.add(new GroceryItemDto(++counter, "Eier", 10, false));
        list.add(new GroceryItemDto(++counter, "Honig", 1, false));
        list.add(new GroceryItemDto(++counter, "Apfelsaft", 2, false));
        list.add(new GroceryItemDto(++counter, "Zucker", 1, false));
        list.add(new GroceryItemDto(++counter, "Brokkoli", 1, false));
        list.add(new GroceryItemDto(++counter, "Kartoffel", 4, false));
        list.add(new GroceryItemDto(++counter, "Butter", 1, false));
    }
    
    @Override
    public GroceryItemDto createGroceryItem(GroceryItemDto product) {
        product.setId(++counter);
        list.add(product);
        return product;
    }
    
    @Override
    public GroceryItemDto updateGroceryItem(GroceryItemDto product) {
        for (int i = 0; i < list.size(); i++) {
            GroceryItemDto item = list.get(i);
            if (Objects.equals(item.getId(), product.getId())) {
                list.set(i, product);
                return product;
            }
        }
        throw new NoSuchElementException("Product mit id " + product.getId() + " nicht gefunden");
    }
    
    @Override
    public GroceryItemDto patchGroceryItem(Long id, boolean collected) {
        for (GroceryItemDto item : list) {
            if (Objects.equals(item.getId(), id)) {
                item.setCollected(collected);
                return item;
            }
        }
        throw new NoSuchElementException("Product mit id " + id + " nicht gefunden");
    }
    
    @Override
    public List<GroceryItemDto> getGroceryItems() {
        return list;
    }
    
    @Override
    public Optional<GroceryItemDto> getGroceryItem(Long id) {
        for (GroceryItemDto item : list) {
            if (Objects.equals(item.getId(), id)) {
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }
    
    @Override
    public void deleteGroceryItem(Long id) {
        if (id == null) {
            list.clear();
        } else {
            for (int i = 0; i < list.size(); i++) {
                GroceryItemDto item = list.get(i);
                if (Objects.equals(item.getId(), id)) {
                    list.remove(i);
                    return;
                }
            }
            throw new NoSuchElementException("Product mit id " + id + " nicht gefunden");
        }
    }
}