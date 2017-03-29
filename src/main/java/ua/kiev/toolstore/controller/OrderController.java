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
import java.util.Optional;


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
        LOG.debug("<===REST+Angular=== Order Saved to DB");
    }



    @RequestMapping(value = {"/detail", "/detail/{id}"})
    public String showOrder(@PathVariable(value = "id") Optional<Long> id,
                            ModelMap model){
        if (id.isPresent()){
            model.addAttribute("activeOrder", orderService.findById(id.get()));
        } else {
            model.addAttribute("activeOrder", orderService.getActiveOrder());
        }
        return "orderDetail";
    }


    @RequestMapping(value = "/item/delete/{itemId}")
    public String deleteItem(@PathVariable Long itemId){
        orderService.deleteLineItem(itemId);
        return "redirect:/order/detail";
    }


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
        LOG.debug("<---Comment to confirmed order: " + comment);
        orderService.confirmOrder(null, comment);
        return "orderConfirmed";
    }


    // ================== Admin methods: order manager =============================================

    @RequestMapping(value = {"/admin/manage/{status}/page/{pageNumber}",
                            "/admin/manage/{status}/page/{pageNumber}/{action}/{id}"})
    public String adminOrderOperate(@PathVariable(value = "status") String status,
                                    @PathVariable(value = "pageNumber") Integer pageNumber,
                                    @PathVariable(value = "action") Optional<String> action,
                                    @PathVariable(value = "id") Optional<Long> id,
                                    ModelMap model) throws IllegalArgumentException {
        if (action.isPresent() && id.isPresent()){
            model.addAttribute("ordersPage", orderService.switchOrderStatus(status, id.get(), action.get(), pageNumber));
        } else {
            model.addAttribute("ordersPage", orderService.findOrderByStatus(status, pageNumber));
        }
        model.addAttribute("orderStatus", status);
        return "orderManager";
    }

}

