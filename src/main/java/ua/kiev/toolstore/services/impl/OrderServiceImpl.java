package ua.kiev.toolstore.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kiev.toolstore.model.Address;
import ua.kiev.toolstore.model.LineItem;
import ua.kiev.toolstore.model.Order;
import ua.kiev.toolstore.model.Product;
import ua.kiev.toolstore.model.enums.OrderStatus;
import ua.kiev.toolstore.repository.LineItemRepository;
import ua.kiev.toolstore.repository.OrderRepository;
import ua.kiev.toolstore.services.AddressService;
import ua.kiev.toolstore.services.OrderService;
import ua.kiev.toolstore.services.ProductService;
import ua.kiev.toolstore.services.UserService;
import ua.kiev.toolstore.util.LoggerWrapper;
import ua.kiev.toolstore.util.MailUtil;
import ua.kiev.toolstore.util.validator.UserUtil;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    protected static final LoggerWrapper LOG = LoggerWrapper.get(OrderServiceImpl.class);

    @Value("${product.item.per.page.admin}")
    private int PAGE_SIZE_ADMIN;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private LineItemRepository lineItemRepository;


    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private AddressService addressService;


    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private UserUtil userUtil;


    // Add LineItem to Order
    @Transactional
    public void add(Long productId) {
        Order order = getActiveOrder();
        Product product = productService.findById(productId);
        order.addItem(new LineItem(product));
        orderRepository.save(order);
        productService.setUnitInStock(product.getId(), product.getUnitInStock() -1);
        LOG.debug("<---Add product to Order");
    }

    // Confirm Order
    @Transactional
    public void confirmOrder(Long orderId, String comment){
        Order order = null;
        if (orderId == null){
            order = getActiveOrder();
        } else {
            order = orderRepository.findById(orderId);
        }

        if (!("Enter your comment...".equals(comment) | comment.trim().isEmpty())){
            order.setComment(comment);
            LOG.debug("<---Confirm Order comment: ( " + comment + " )");
        }

        order.setOrderStatus(OrderStatus.CONFIRMED);
        orderRepository.save(order);
        LOG.debug("<-- Confirm Order. Send e-mail to Administrator.");
        mailUtil.sendMailAboutConfirmedOrder(order);
    }


    public Order findById(Long id) {
        return orderRepository.findById(id);
    }


    public void delete(Long id) {
        orderRepository.delete(id);
    }

    // Delete Orders by UserId - used when admin delete a particular user
    public void deleteAllOrdersByUserId(Long userId) {
        orderRepository.deleteByUserId(userId);
    }



    // Find ALL ORDERS of ALL USERS with required STATUS
    public List<Order> findByOrderStatus(OrderStatus orderStatus) {
        return orderRepository.findByOrderStatus(orderStatus);
    }

    // Get active order
    public Order getActiveOrder() {
        Order order = orderRepository.findByUserIdAndOrderStatus(userUtil.getUserId(), OrderStatus.ACTIVE);
        if (order == null){
            LOG.debug("<---Order does not exist, creating new order");
            order = new Order(userService.findById(userUtil.getUserId()));
            orderRepository.save(order);
            order = orderRepository.findByUserIdAndOrderStatus(userUtil.getUserId(), OrderStatus.ACTIVE);
        }
        return order;
    }


    public Page<Order> findOrderByStatus(String status, Integer pageNumber) throws IllegalArgumentException{
        PageRequest request = new PageRequest(pageNumber, PAGE_SIZE_ADMIN);

        if (status.equalsIgnoreCase("all")){
            //return orderRepository.findAllByOrderByOrderStatusAsc(request);
            return orderRepository.findAllByOrderByIdDesc(request);
        }
        return orderRepository.findByOrderStatus(OrderStatus.valueOf(status.toUpperCase()), request);
    }


    //Change ORDER status of particular USER
    public void changeStatus(Long orderId, OrderStatus orderStatus) {
        orderRepository.changeStatus(orderId, orderStatus.toString());
    }


    // Administrator's Order manager switcher
    @Transactional
    public Page<Order> switchOrderStatus(String status, Long orderId,
                                         String action, Integer pageNumber) throws IllegalArgumentException{

        if (action.equalsIgnoreCase("DECLINED") || action.equalsIgnoreCase("CANCELED")){
            Order order = orderRepository.findById(orderId);
            List<LineItem> lineItems = order.getLineItems();
            for (LineItem item : lineItems) {
                item.getProduct().setUnitInStock(item.getProduct().getUnitInStock()+1);
                item.setAmount(item.getAmount() - 1);
            }
            order.setLineItems(lineItems);
            orderRepository.save(order);
        }

        changeStatus(orderId, OrderStatus.valueOf(action.toUpperCase()));
        PageRequest request = new PageRequest(pageNumber, PAGE_SIZE_ADMIN);

        if (status.equalsIgnoreCase("all")){
            return orderRepository.findAllByOrderByIdDesc(request);
        }

        return orderRepository.findByOrderStatus(OrderStatus.valueOf(status.toUpperCase()), request);
    }



    /*    Get Address specific to Order
    *     (by default Address is the same that User entered on registration,
    *     after first modifying Address attain unique ID - different from User's address)
    */
    @Transactional
    public void changeOrderAddress(Address address, Long orderId){
        Order order = null;

        if (orderId == null){
            order = getActiveOrder();
        } else {
            order = orderRepository.findById(orderId);
        }

        if (address.getId().equals(order.getUser().getAddress().getId())){
            address.setId(null);
        }

        address = addressService.saveAndFlush(address);
        order.setAddress(address);
        orderRepository.save(order);
    }


    //===================== LineItem Repository ======================================

    // Clear all items in particular Order
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


    // Sum all items in order
    public Double sumAllItemsInOrder(Long orderId) {
        if (orderId == null){
            orderId = getActiveOrderId();
        }
        return lineItemRepository.sumAllItemsInOrder(orderId);
    }


    // Count all item's in Order
    public Long countLineItemByOrderId(Long orderId) {
        if (orderId == null){
            orderId = getActiveOrderId();
        }
        return lineItemRepository.countLineItemByOrderId(orderId);
    }


    // Count quantity of Products in Order
    public Integer countItemsInActiveOrderOfUser(Long userId) {
        return lineItemRepository.countItemsInActiveOrderOfUser(userId);
    }


    // Delete item from Order
    @Transactional
    public void deleteLineItem(Long itemId) {
        Product product = lineItemRepository.findById(itemId).getProduct();
        productService.setUnitInStock(product.getId(), product.getUnitInStock() + 1);
        lineItemRepository.delete(itemId);
    }


    // ================= Private methods =================================

    private Long getActiveOrderId(){
        Long activeOrderID = orderRepository.findByUserIdAndOrderStatus(userUtil.getUserId(), OrderStatus.ACTIVE).getId();
        if (activeOrderID == null){
            activeOrderID = getActiveOrder().getId();
        }
        return activeOrderID;
    }

}
