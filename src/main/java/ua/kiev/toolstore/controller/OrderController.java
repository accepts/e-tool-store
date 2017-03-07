package ua.kiev.toolstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ua.kiev.toolstore.model.Address;
import ua.kiev.toolstore.model.Order;
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




    // ===================== Confirm ORDER methods ===========================

    @RequestMapping(value = "/confirm/address")
    public String confirmOrder(ModelMap model){
        Order order = orderService.getActiveOrder();

        if (order.getLineItems().size() == 0){
            return  "redirect:/order/detail";
        }

        model.addAttribute("address", order.getAddress());
        return "orderConfirmAddress";
    }


    @RequestMapping(value = "/confirm/address", method = RequestMethod.POST, params = {"save"})
    public String confirmOrdersAddress(Address address){
        LOG.debug("<---order change address info {}");
        orderService.changeOrderAddress(address, null);
        return "redirect:/order/confirm/address";
    }


    @RequestMapping(value = "/confirm/comment", method = RequestMethod.GET)
    public String confirmedOrder(ModelMap model){
        Order order = orderService.getActiveOrder();

        if (order.getLineItems().size() == 0){
            return  "redirect:/order/detail";
        }

        model.addAttribute("order", order);
        return "orderConfirmComment";
    }


    @RequestMapping(value = "/confirm/comment", method = RequestMethod.POST, params = {"saveComment"})
    public String confirmOrderComment(HttpServletRequest req){
        String comment = String.valueOf(req.getParameter("commentToOrder"));
        orderService.confirmOrder(null, comment);
        return "orderConfirmed";
    }


    @RequestMapping(value = "/admin/manage/{status}/page/{pageNumber}")
    public String adminOrderManagePage(@PathVariable String status,
                                       @PathVariable Integer pageNumber,
                                       ModelMap model) throws IllegalArgumentException{
        model.addAttribute("ordersPage", orderService.findOrderByStatus(status, pageNumber))
                .addAttribute("orderStatus", status);
        return "orderManager";
    }


    @RequestMapping(value = "/order/admin/manage/{status}/page/{pageNumber}/{action}/{id}")
    public String adminOrderOperate(@PathVariable String status,
                                    @PathVariable Integer pageNumber,
                                    @PathVariable String action,
                                    @PathVariable Long id){



        return "orderManager";
    }





    //============================ EXCEPTION handler ===========================================
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleClientErrors(Exception e) {
        LOG.warn("<====E==== IllegalArgumentEXCEPTION occur {}" + e.getMessage());
        return "redirect:/home";
    }

}





























