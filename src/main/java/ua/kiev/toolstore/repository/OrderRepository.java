package ua.kiev.toolstore.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ua.kiev.toolstore.model.Order;
import ua.kiev.toolstore.model.enums.OrderStatus;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

    Order findById(Long id);

    Order findByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);

    List<Order> findByOrderStatus(OrderStatus orderStatus);

    @Transactional
    @Modifying
    @Query(value = "UPDATE orders o SET orderstatus = ?2 WHERE o.id = ?1", nativeQuery = true)
    void changeStatus(Long orderId, String status);


    //TODO add comment to repo


}
