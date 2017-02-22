package ua.kiev.toolstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.kiev.toolstore.util.LoggerWrapper;
import ua.kiev.toolstore.util.validator.UserUtil;


@RestController
@RequestMapping(value = "/order")
public class OrderController {


    protected static final LoggerWrapper LOG = LoggerWrapper.get(OrderController.class);

    @Autowired
    private UserUtil userUtil;

    @RequestMapping(value = "/add/{productId}", method = RequestMethod.PUT)
    public void addProductToOrder(@PathVariable Long productId){




//        Long userId = userUtil.getUserId();
//        if (userId == null){
//            LOG.debug("<===REST---User is NOT LogIN");
//        }
//        LOG.debug("<<===REST---(Add product to Cart) where product ID: (" + productId + ") and UserID: (" + userId + ")");
//


    }




}
