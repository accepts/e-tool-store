package ua.kiev.toolstore.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;
import ua.kiev.toolstore.model.LineItem;

import java.util.List;

public interface LineItemRepository extends CrudRepository<LineItem, Long> {

    LineItem findById(Long id);

    // Calculate sum all item's
    @Async
    @Query(value = "SELECT COUNT(*) FROM lineitem li WHERE li.order_id = ?1", nativeQuery = true)
    Long countLineItemByOrderId(Long orderId);


    // Calculate sum all item's of particular Order
    @Async
    @Query(value = "SELECT SUM(li.price * li.amount) FROM lineitem li WHERE li.order_id = ?1", nativeQuery = true)
    Double sumAllItemsInOrder(Long orderId);


    // Delete all LineItem of particular Order
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM lineitem li WHERE li.order_id = ?1", nativeQuery = true)
    void clearOrder(Long orderId);


    // Delete LineItem When some product deleting from DB
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM lineitem li WHERE li.product_id = ?1", nativeQuery = true)
    void deleteItemWithProductId(Long productId);

    @Async
    @Query(value = "SELECT COUNT(li.amount) FROM lineitem li, orders o WHERE o.orderstatus = 'ACTIVE' AND o.user_id = ?1 AND o.id = li.order_id", nativeQuery = true)
    Integer countItemsInActiveOrderOfUser(Long userId);

}



