package ua.kiev.toolstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.kiev.toolstore.services.OrderService;
import ua.kiev.toolstore.util.LoggerWrapper;


@RestController
@RequestMapping(value = "/order")
public class OrderController {

    protected static final LoggerWrapper LOG = LoggerWrapper.get(OrderController.class);

    @Autowired
    private OrderService orderService;



    @RequestMapping(value = "/add/{productId}", method = RequestMethod.PUT)
    public void addProductToOrder(@PathVariable Long productId){

//        Order order = orderService.getActiveOrder();
//        LOG.debug("<===REST---getActiveOrder|| userID: (" + order.getUser().getId()
//                + ") and status (" + order.getOrderStatus() + ")");

        orderService.save(productId);

    }




}
