package ua.kiev.toolstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.kiev.toolstore.model.Address;
import ua.kiev.toolstore.model.enums.Role;
import ua.kiev.toolstore.model.security.AuthorizedUser;
import ua.kiev.toolstore.model.security.User;
import ua.kiev.toolstore.services.UserService;
import ua.kiev.toolstore.util.LoggerWrapper;
import ua.kiev.toolstore.util.validator.UserUtil;

import java.util.Collection;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    protected static final LoggerWrapper LOG = LoggerWrapper.get(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserUtil userValidator;


    //=====================  User REGISTER  ====================================

    @RequestMapping(value = "/register")
    public String userRegister(User user, ModelMap model) {
        LOG.info("<===USER-register form:");
        Address address = new Address();
        user.setAddress(address);
        model.addAttribute("user", user);
        return "userRegister";
    }


    @RequestMapping(value = "/register", params = {"save"})
    public String registerCustomer(User user, BindingResult bindingResult, ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "userRegister";
        }
        //Validate user's inputs
        if (!userValidator.userFieldsValidator(user)){
            LOG.info("<---Validation of the USER is occur! Fields has errors!");
            bindingResult.reject("validation.error.user.fields.message");
            return "userRegister";
        }
        // Validate duplicate (on email)
        if (!userValidator.userDuplicateValidator(user)){
            LOG.info("<---Validation of the USER is occur! Fields has errors!");
            bindingResult.reject("validation.error.user.duplicate.message");
            return "userRegister";
        }
        LOG.info("<===SAVE USER to REPOSITORY {}:", user);
        userService.save(user);
        model.clear();
        return "redirect:/login";
    }


    //===================  User MANAGER ========================================

    @RequestMapping(value = "/admin/manager")
    public String userManager(ModelMap model){
        LOG.debug("<<---ADMIN-PERSONS page is requested");
        model.addAttribute("users", userService.findAll());
        return "userManager";
    }

    // Lock-Unlock user
    @RequestMapping(value = "/admin/manager/reset_user/{id}/{value}")
    public String resetUser(@PathVariable Long id,
                            @PathVariable boolean value){
        LOG.debug("<---BUTTON IS PRESS (User RESET)--- ID: ( " + id + " ) | Value : ( " + value + " );");
        userService.resetUser(id, value);
        return "redirect:/user/admin/manager";
    }

    // Delete user
    @RequestMapping(value = "/admin/manager/delete_user/{id}")
    public String deleteUser(@PathVariable Long id){
        LOG.debug("<---BUTTON IS PRESS (User DELETE ID: ( " + id + " )");
        userService.delete(id);
        return "redirect:/user/admin/manager";
    }



    }
