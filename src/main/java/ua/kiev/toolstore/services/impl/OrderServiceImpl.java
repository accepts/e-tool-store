package ua.kiev.toolstore.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kiev.toolstore.model.LineItem;
import ua.kiev.toolstore.model.Order;
import ua.kiev.toolstore.model.enums.OrderStatus;
import ua.kiev.toolstore.repository.LineItemRepository;
import ua.kiev.toolstore.repository.OrderRepository;
import ua.kiev.toolstore.repository.ProductRepository;
import ua.kiev.toolstore.repository.UserRepository;
import ua.kiev.toolstore.services.OrderService;
import ua.kiev.toolstore.util.LoggerWrapper;
import ua.kiev.toolstore.util.validator.UserUtil;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    protected static final LoggerWrapper LOG = LoggerWrapper.get(OrderServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserUtil userUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private LineItemRepository lineItemRepository;


    public void save(Long productId) {
//        Order order = orderRepository.findByUserIdAndOrderStatus(userUtil.getUserId(), OrderStatus.ACTIVE);
//
//        if (order == null){
//            order = new Order(userRepository.findById(userUtil.getUserId()));
//            LOG.debug("<--Order is NULL!!!, creating new order");
//        }

        Order order = getActiveOrder();
        order.addItem(new LineItem(productRepository.findById(productId)));
        orderRepository.save(order);
        LOG.debug("<--REPO end saving to repo! ");
    }


    public Order findById(Long id) {
        return orderRepository.findById(id);
    }


    public void delete(Long id) {
        orderRepository.delete(id);
    }

    // Find ALL ORDERS of ALL USERS with required STATUS
    public List<Order> findByOrderStatus(OrderStatus orderStatus) {
        return orderRepository.findByOrderStatus(orderStatus);
    }

    //Change ORDER status of particular USER
    public void changeStatus(Long orderId, OrderStatus orderStatus) {
        orderRepository.changeStatus(orderId, orderStatus.toString());
    }


    public Order getActiveOrder() {
        Order order = orderRepository.findByUserIdAndOrderStatus(userUtil.getUserId(), OrderStatus.ACTIVE);
        if (order == null){
            LOG.debug("<===ORDER=== is NULL!!!, creating new order");
            order = new Order(userRepository.findById(userUtil.getUserId()));
            orderRepository.save(order);
            order = orderRepository.findByUserIdAndOrderStatus(userUtil.getUserId(), OrderStatus.ACTIVE);
        }
        return order;
    }


    //TODO check
    private Long getActiveOrderId(){
        Long activeOrderID = orderRepository.findByUserIdAndOrderStatus(userUtil.getUserId(), OrderStatus.ACTIVE).getId();
//        Assert.notNull(activeOrderID, "ACTIVE orderId is NULL, ORDER does not exist");
        if (activeOrderID == null){
            activeOrderID = getActiveOrder().getId();
        }
        return activeOrderID;
    }


    //===================== LineItem Repository ======================================

    //TODO
    public void clearOrder(Long orderId) {
        if (orderId == null){
            orderId = getActiveOrderId();
        }
        lineItemRepository.clearOrder(orderId);
    }


    public Double sumAllItemsInOrder(Long orderId) {
        if (orderId == null){
            orderId = getActiveOrderId();
        }
        return lineItemRepository.sumAllItemsInOrder(orderId);
    }


    public Long countLineItemByOrderId(Long orderId) {
        if (orderId == null){
            orderId = getActiveOrderId();
        }
        return lineItemRepository.countLineItemByOrderId(orderId);
    }


    public void deleteLineItem(Long itemId) {
        lineItemRepository.delete(itemId);
    }


    public Long countCartItems(Long orderId){
        if (orderId == null){
            orderId = getActiveOrderId();
        }
        return lineItemRepository.countLineItemByOrderId(orderId);
    }


}
