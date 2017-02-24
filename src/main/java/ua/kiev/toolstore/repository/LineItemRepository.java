package ua.kiev.toolstore.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;
import ua.kiev.toolstore.model.LineItem;

import java.util.List;

public interface LineItemRepository extends CrudRepository<LineItem, Long> {

    LineItem findById(Long id);

    //TODO find all items by orderId
//    List<LineItem> findByOrderId(Long orderId);

    // Calculate sum all item's
    @Async
    @Query(value = "SELECT COUNT(*) FROM lineitem li WHERE li.order_id = ?1", nativeQuery = true)
    Long countLineItemByOrderId(Long orderId);


    // Calculate sum all item's of particular Order
    @Async
    @Query(value = "SELECT SUM(li.price * li.amount) FROM lineitem li WHERE li.order_id = ?1", nativeQuery = true)
    Double sumAllItemsInOrder(Long orderId);


    // Delete all LineItem of particular Order
    @Query(value = "DELETE FROM lineitem li WHERE li.order_id = ?1", nativeQuery = true)
    void clearOrder(Long orderId);

}
