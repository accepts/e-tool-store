package ua.kiev.toolstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.kiev.toolstore.services.OrderService;
import ua.kiev.toolstore.util.LoggerWrapper;


@Controller
@RequestMapping(value = "/order")
public class OrderController {

    protected static final LoggerWrapper LOG = LoggerWrapper.get(OrderController.class);

    @Autowired
    private OrderService orderService;


    @ResponseBody
    @RequestMapping(value = "/add/{productId}", method = RequestMethod.PUT)
    public void addProductToOrder(@PathVariable Long productId){
        orderService.save(productId);
        LOG.debug("<--- Order Saved to DB");
    }


    //TODO DELETE this DUMMY method
    @RequestMapping(value = "/add/dummy/{productId}")
    public String addProductToOrderDummy(@PathVariable Long productId){
        LOG.debug("<---ORDER-SERV-CONTROLLER ---- START...");
        orderService.save(productId);
        LOG.debug("<---ORDER-SERV-CONTROLLER ---- END!");
        return "redirect:/home";
    }


    @RequestMapping(value = "/detail")
    public String showOrder(ModelMap model){
        model.addAttribute("activeOrder", orderService.getActiveOrder());
        return "orderDetail";
    }



}





























