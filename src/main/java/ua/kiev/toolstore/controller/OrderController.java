package ua.kiev.toolstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.kiev.toolstore.model.Address;
import ua.kiev.toolstore.services.OrderService;
import ua.kiev.toolstore.util.LoggerWrapper;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping(value = "/order")
public class OrderController {

    protected static final LoggerWrapper LOG = LoggerWrapper.get(OrderController.class);

    @Autowired
    private OrderService orderService;


    @ResponseBody
    @RequestMapping(value = "/add/{productId}", method = RequestMethod.PUT)
    public void addProductToOrder(@PathVariable Long productId){
        orderService.add(productId);
        LOG.debug("<--- Order Saved to DB");
    }


    //TODO DELETE this DUMMY method
    @RequestMapping(value = "/add/dummy/{productId}")
    public String addProductToOrderDummy(@PathVariable Long productId){
        LOG.debug("<---ORDER-SERV-CONTROLLER ---- START...");
        orderService.add(productId);
        LOG.debug("<---ORDER-SERV-CONTROLLER ---- END!");
        return "redirect:/home";
    }


    @RequestMapping(value = "/detail")
    public String showOrder(ModelMap model){
        model.addAttribute("activeOrder", orderService.getActiveOrder());
        return "orderDetail";
    }

    //TODO Angular
    //-----------Delete ITEM from ORDER
    @RequestMapping(value = "/item/delete/{itemId}")
    public String deleteItem(@PathVariable Long itemId){
        orderService.deleteLineItem(itemId);
        return "redirect:/order/detail";
    }

    //TODO Angular
    //----------Clear all ITEM of active ORDER
    @RequestMapping(value = "/clear/{orderId}")
    public String clearOrder(@PathVariable Long orderId){
        orderService.clearOrder(orderId);
        return "redirect:/order/detail";
    }




    // ===================== Confirm ORDER ===========================

    @RequestMapping(value = "/confirm/address")
    public String confirmOrder(ModelMap model){
        Address address = orderService.getActiveOrder().getAddress();
        model.addAttribute("address", address);
        return "orderConfirmAddress";
    }


    @RequestMapping(value = "/confirm/address", method = RequestMethod.POST, params = {"save"})
    public String confirmOrdersAddress(Address address){
        LOG.debug("<---order change address info {}");
        orderService.changeOrderAddress(address, null);
        return "redirect:/order/confirm/address";
    }























    @RequestMapping(value = "/confirm/comment")
    public String confirmOrderComment(ModelMap model){
        String commentToOrder = "<Enter your comment to order>";
        model.addAttribute("commentToOrder", commentToOrder);
        return "orderConfirmComment";
    }


    @RequestMapping(value = "/confirm/comment", method = RequestMethod.POST, params = {"saveComment"})
    public String confirmOrderComment(ModelMap model, HttpServletRequest req){
        String comment = String.valueOf(req.getParameter("commentToOrder"));
        if (comment.trim().isEmpty()){
            //DON'T SAVE comment
            LOG.debug("<<===COMMENT is Empty! ");
        } else{
            //SAVE comment oto ORDER
            LOG.debug("<===ORDER COMMENT INFO!!!==== ( "  + comment + " )");
            //Order order = orderService.getActiveOrder();
            //order.setComment(comment);
        }
        //TODO save comment to REPO or redirect Comment to orderConfirmed
        return "redirect:/order/confirm/comment";
    }


    @RequestMapping(value = "/confirm/confirmed")
    public String confirmedOrder(ModelMap model){
//        String commentToOrder = "<Enter your comment to order>";
        return "orderConfirmed";
    }
}





























