package ua.kiev.toolstore.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
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
    private OrderRepository repository;

    @Autowired
    private UserUtil userUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private LineItemRepository lineItemRepository;


    public void save(Long productId) {
        Order order = repository.findByUserIdAndOrderStatus(userUtil.getUserId(), OrderStatus.ACTIVE);

        if (order == null){
            order = new Order(userRepository.findById(userUtil.getUserId()));
            LOG.debug("<--Order is NULL!!!, creating new order");
        }

        order.addItem(new LineItem(productRepository.findById(productId)));
        LOG.debug("<---REPO START SAVING TO REPO! ");
        repository.save(order);
        LOG.debug("<--REPO end saving to repo! ");
    }


    public Order findById(Long id) {
        return repository.findById(id);
    }


    public void delete(Long id) {
        repository.delete(id);
    }


    public List<Order> findByOrderStatus(OrderStatus orderStatus) {
        return repository.findByOrderStatus(orderStatus);
    }


    public void changeStatus(Long orderId, OrderStatus orderStatus) {
        repository.changeStatus(orderId, orderStatus.toString());
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


    //----------------- Additional methods ------------------------------

    private Long getActiveOrderId(){
        Long activeOrderID = repository.findByUserIdAndOrderStatus(userUtil.getUserId(), OrderStatus.ACTIVE).getId();
        Assert.notNull(activeOrderID, "ACTIVE orderId is NULL, ORDER does not exist");
        return activeOrderID;
    }

}
