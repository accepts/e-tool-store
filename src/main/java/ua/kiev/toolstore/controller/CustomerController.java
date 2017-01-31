package ua.kiev.toolstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.kiev.toolstore.model.Customer;
import ua.kiev.toolstore.model.ShippingAddress;
import ua.kiev.toolstore.model.User;
import ua.kiev.toolstore.model.enums.Role;
import ua.kiev.toolstore.services.CustomerService;
import ua.kiev.toolstore.util.LoggerWrapper;

import java.util.EnumSet;

@Controller
@RequestMapping(value = "/customer")
public class CustomerController {

    protected static final LoggerWrapper LOG = LoggerWrapper.get(CustomerController.class);


    @Autowired
    private CustomerService customerService;


    @RequestMapping(value = "/create")
    public String customerRegister(Customer customer, ModelMap model) {
        LOG.info("<==============Create customer form:");

        User user = new User();
        ShippingAddress address = new ShippingAddress();
        customer.setUser(user);
        customer.setAddress(address);

        model.addAttribute("customer", customer);

        LOG.info("<==============Create customer form + customer in model :", customer);

        return "customerRegister";
    }




    //  **************************** CREATE (+ Edit) Customer ****************************

    @RequestMapping(value = "/create", params = {"save"})
    public String registerCustomer(Customer customer, BindingResult bindingResult, ModelMap model) {
        LOG.info("<====!!!======SAVE CUSTOMER before ======= {}:", customer);

        customer.getUser().setRoles(EnumSet.of(Role.ROLE_CUSTOMER));

        LOG.info("<====!!!======SAVE CUSTOMER after ======= {}:", customer);

        customerService.save(customer);

        model.clear();
        return "redirect:/customer/create";
    }


    }
