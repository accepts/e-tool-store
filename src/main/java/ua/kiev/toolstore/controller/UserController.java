package ua.kiev.toolstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.kiev.toolstore.model.enums.Role;
import ua.kiev.toolstore.model.security.AuthorizedUser;
import ua.kiev.toolstore.model.security.User;
import ua.kiev.toolstore.services.UserService;
import ua.kiev.toolstore.util.LoggerWrapper;
import ua.kiev.toolstore.util.validator.UserValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.EnumSet;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    protected static final LoggerWrapper LOG = LoggerWrapper.get(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;











/*  TODO Admin area with all users with lock-unlock option

    // LIST of ALL customer(ROLE = ADMIN)
    @RequestMapping(value = "/admin/persons")
    public String adminPAge(ModelMap model) {
        LOG.debug("<<---ADMIN-PERSONS page is requested");

        model.addAttribute("user_roles", getRoles())
                .addAttribute("user_name", getUserName())
                .addAttribute("id_logged_user", getID())
                .addAttribute("user_email", getEmail());

        model.addAttribute("userList", service.findAll());
        return "persons";
    }



    // TURN-OFF\ON User
    @RequestMapping(value = "/admin/reset_user/{id}/{value}")
    public String resetUser(@PathVariable Long id,
                            @PathVariable boolean value){
        LOG.debug("<---BUTTON IS PRESS (User Reset)--- ID: ( " + id + " ) | Value : ( " + value + " );");
        service.resetUser(id, value);
        return "redirect:/admin/persons";
    }


    */


    //=====================  REGISTER  ==========================================

    @RequestMapping(value = "/register")
    public String createUser(User user, ModelMap model) {
        LOG.debug("<<---CREATE-PERSONS page is requested");
        model.addAttribute("user_roles", user)
                .addAttribute("user_name", getUserName());
        return "registerCustomer";
    }


    @RequestMapping(value = "/register", params = {"save"})
    public String createUserPost(User user, BindingResult bindingResult, ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "registerCustomer";
        }
//        TODO set Role into Service layer
        user.setRoles(EnumSet.of(Role.ROLE_CUSTOMER));
        LOG.debug("<<---SAVE TO REPOSITORY {}", user);
        userService.save(user);
        model.clear();
        //TODO set new user credentials into coresponding fields
        return "redirect:/login";
    }




    //==================== LOGIN + LOGOUT + DENIED ==============================

    @RequestMapping(value = "/login")
    public String login(){
        //TODO error message on wrong login
        return "login";
    }

//    @RequestMapping("/login-error")
//    public String loginError(Model model) {
//        model.addAttribute("loginError", true);
//        return "login";
//    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        LOG.debug("<<--- LOGOUT page is requested");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "home";
    }

    @RequestMapping(value = "/denied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("user_roles", getRoles());
        model.addAttribute("user_name", getUserName());
        return "accessDenied";
    }

    //*********************************************************************************************
    //******************************* Addition Methods ********************************************
    //*********************************************************************************************

    private Collection<Role> getRoles(){
        Collection<Role> roles = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            roles = (Collection<Role>) ((UserDetails)principal).getAuthorities();
            return roles;
        } else {
            return null;
        }
    }

    private String getUserName(){
        String name = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            name = ((UserDetails)principal).getUsername();
            return name;
        } else {
            return null;
        }
    }

    private Long getID(){
        Long id = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            id = ((AuthorizedUser)principal).id();
            return id;
        } else {
            return null;
        }
    }

    private String getEmail(){
        String email = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            email = ((AuthorizedUser)principal).getUserWrapper().getEmail();
            return email;
        } else {
            return null;
        }
    }




    }


/*

    @RequestMapping(value = "/create")
    public String userRegister(User user, ModelMap model) {
        LOG.info("<==============USER-register form:");
        Address address = new Address();
        user.setAddress(address);
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










 */



