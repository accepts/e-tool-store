package ua.kiev.toolstore.services;

import ua.kiev.toolstore.model.Order;
import ua.kiev.toolstore.model.enums.OrderStatus;

import java.util.List;

public interface OrderService {

    Order findById(Long id);

    void save(Long productId);

    void delete(Long id);

    List<Order> findByOrderStatus(OrderStatus orderStatus);

    void changeStatus(Long orderId, OrderStatus orderStatus);

    Order getActiveOrder();

    // ==================== LineItem Repository =============================

    void clearOrder(Long orderId);

    Double sumAllItemsInOrder(Long orderId);

    Long countLineItemByOrderId(Long orderId);

    void deleteLineItem(Long itemId);
}
