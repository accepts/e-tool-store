package ua.kiev.toolstore.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kiev.toolstore.model.LineItem;
import ua.kiev.toolstore.model.Order;
import ua.kiev.toolstore.model.Product;
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


    // Add LineItem to Order
    @Transactional
    public void add(Long productId) {
        Order order = getActiveOrder();
        Product product = productRepository.findById(productId);
        order.addItem(new LineItem(product));
        //orderRepository.save(order);
        save(order);
        productRepository.setUnitInStock(product.getId(), product.getUnitInStock() -1);
        LOG.debug("<--REPO end saving to repo! ");
    }

    //TODO
    @Transactional
    public void confirmOrder(Order order){
        order.setOrderStatus(OrderStatus.CONFIRMED);
        save(order);
        LOG.debug("<===ORDER IS CONFIRMED into SERVICE! ");
        //TODO send e-mail to Admin
    }

    public Order findById(Long id) {
        return orderRepository.findById(id);
    }


    public void delete(Long id) {
        orderRepository.delete(id);
    }


    public void save(Order order){
        orderRepository.save(order);
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
            LOG.debug("<---Order does not exist, creating new order");
            order = new Order(userRepository.findById(userUtil.getUserId()));
            orderRepository.save(order);
            order = orderRepository.findByUserIdAndOrderStatus(userUtil.getUserId(), OrderStatus.ACTIVE);
        }
        return order;
    }


    private Long getActiveOrderId(){
        Long activeOrderID = orderRepository.findByUserIdAndOrderStatus(userUtil.getUserId(), OrderStatus.ACTIVE).getId();
        if (activeOrderID == null){
            activeOrderID = getActiveOrder().getId();
        }
        return activeOrderID;
    }


    //===================== LineItem Repository ======================================

    @Transactional
    public void clearOrder(Long orderId) {
        if (orderId == null){
            orderId = getActiveOrderId();
        }

        Order order =  getActiveOrder();
        List<LineItem> lineItems = order.getLineItems();

        for (LineItem item : lineItems) {
            item.getProduct().setUnitInStock(item.getProduct().getUnitInStock()+1);
        }

        order.setLineItems(lineItems);
        orderRepository.save(order);
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


    @Transactional
    public void deleteLineItem(Long itemId) {
        Product product = lineItemRepository.findById(itemId).getProduct();
        productRepository.setUnitInStock(product.getId(), product.getUnitInStock() + 1);
        lineItemRepository.delete(itemId);
    }


    public Long countCartItems(Long orderId){
        if (orderId == null){
            orderId = getActiveOrderId();
        }
        return lineItemRepository.countLineItemByOrderId(orderId);
    }





}
