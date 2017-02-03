package ua.kiev.toolstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.kiev.toolstore.model.ShippingAddress;
import ua.kiev.toolstore.model.User;
import ua.kiev.toolstore.services.UserService;
import ua.kiev.toolstore.util.LoggerWrapper;
import ua.kiev.toolstore.util.validator.UserValidator;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    protected static final LoggerWrapper LOG = LoggerWrapper.get(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;


    @RequestMapping(value = "/create")
    public String userRegister(User user, ModelMap model) {
        LOG.info("<==============USER-register form:");
        ShippingAddress address = new ShippingAddress();
        user.setShippingAddress(address);
        model.addAttribute("user", user);
        return "userRegister";
    }


    //  **************************** REGISTER Customer ****************************

    @RequestMapping(value = "/create", params = {"save"})
    public String registerCustomer(User user, BindingResult bindingResult, ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "userRegister";
        }

        //Validate user
        if (!userValidator.userFieldsValidator(user)){
            LOG.info("<-------Validation of the USER is occur! Fields has errors!");
            bindingResult.reject("validation.error.user.fields.message");
            return "userRegister";
        }

        // Validate duplicate
        if (!userValidator.userDuplicateValidator(user)){
            LOG.info("<-------Validation of the USER is occur! Fields has errors!");
            bindingResult.reject("validation.error.user.duplicate.message");
            return "userRegister";
        }

        LOG.info("<====!!!======SAVE USER  ======= {}:", user);
        userService.save(user);
        model.clear();
        return "redirect:/user/create";
    }




    }


