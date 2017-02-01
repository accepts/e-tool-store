package ua.kiev.toolstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.kiev.toolstore.model.Customer;
import ua.kiev.toolstore.model.User;
import ua.kiev.toolstore.model.enums.Role;
import ua.kiev.toolstore.services.CustomerService;
import ua.kiev.toolstore.services.UserService;
import ua.kiev.toolstore.util.LoggerWrapper;

import java.util.EnumSet;

@Controller
@RequestMapping(value = "/customer")
public class CustomerController {

    protected static final LoggerWrapper LOG = LoggerWrapper.get(CustomerController.class);


    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

//    @Autowired
//    private ShippingAddressService shippingAddressService;

    @RequestMapping(value = "/create")
    public String customerRegister(Customer customer, ModelMap model) {
        LOG.info("<==============Create customer form:");

        User user = new User();
//        ShippingAddress address = new ShippingAddress();
        customer.setUser(user);
//        customer.setShippingAddress(address);

        model.addAttribute("customer", customer);

        LOG.info("<==============Create customer form + customer in model :", customer);

        return "customerRegister";
    }




    //  **************************** CREATE (+ Edit) Customer ****************************

    @RequestMapping(value = "/create", params = {"save"})
    public String registerCustomer(Customer customer, BindingResult bindingResult, ModelMap model) {

//        1
//        User user = customer.getUser();
//        user.setRoles(EnumSet.of(Role.ROLE_CUSTOMER));
//        user.setRoles(EnumSet.of(Role.ROLE_CUSTOMER));

        customer.getUser().setRoles(EnumSet.of(Role.ROLE_CUSTOMER));

        //user = userService.saveAndFlush(user);
        //customer.setUser(user);
        LOG.info("<====!!!======SAVE CUSTOMER  ======1= {}:", customer);


//       2
//        ShippingAddress address = customer.getShippingAddress();
//        address= shippingAddressService.saveAndFlush(address);
//        customer.setShippingAddress(address);
//        LOG.info("<====!!!======SAVE CUSTOMER  ======2= {}:", customer);

//        3
        customerService.save(customer);
//        user.setCustomer(customer);
//        address.setCustomer(customer);
//        LOG.info("<====!!!======SAVE CUSTOMER  ======3= {}:", customer);
//        userService.save(user);
//        shippingAddressService.save(address);

        LOG.info("<====!!!======SAVE CUSTOMER  ======4= {}:", customer);
//        LOG.info("<====!!!======SAVE USER  ======5= {}:", user);
//        LOG.info("<====!!!======SAVE ADDRESS  ======6= {}:", address);

//        customerService.save(customer);

        model.clear();
        return "redirect:/customer/create";
    }


    }

/*
1
save-retrieve user
set user to customer
2
save-retrieve address
set address to customer
3
save-retrieve customer
get id customer
4
set customer_id to user
set customer_id to address

 */