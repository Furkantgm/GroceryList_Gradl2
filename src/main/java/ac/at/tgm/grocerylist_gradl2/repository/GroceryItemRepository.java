package ac.at.tgm.grocerylist_gradl2.repository;

import ac.at.tgm.grocerylist_gradl2.entity.GroceryItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroceryItemRepository extends JpaRepository<GroceryItemEntity, Long> {
}
