package ua.kiev.toolstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.kiev.toolstore.model.ShippingAddress;
import ua.kiev.toolstore.model.User;
import ua.kiev.toolstore.model.enums.Role;
import ua.kiev.toolstore.services.UserService;
import ua.kiev.toolstore.util.LoggerWrapper;

import java.util.EnumSet;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    protected static final LoggerWrapper LOG = LoggerWrapper.get(UserController.class);


    @Autowired
    private UserService userService;


    @RequestMapping(value = "/create")
    public String userRegister(User user, ModelMap model) {
        LOG.info("<==============Create USER form:");
        ShippingAddress address = new ShippingAddress();
        user.setShippingAddress(address);
        model.addAttribute("user", user);
        LOG.info("<==============Create USER form + user in model :", user);
        return "userRegister";
    }

    //  **************************** CREATE (+ Edit) Customer ****************************

    @RequestMapping(value = "/create", params = {"save"})
    public String registerCustomer(User user, BindingResult bindingResult, ModelMap model) {
        user.setRoles(EnumSet.of(Role.ROLE_CUSTOMER));
        LOG.info("<====!!!======SAVE USER  ======1= {}:", user);
        userService.save(user);
        model.clear();
        return "redirect:/user/create";
    }




    }


