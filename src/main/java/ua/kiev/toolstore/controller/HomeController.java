package ua.kiev.toolstore.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.kiev.toolstore.util.LoggerWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
public class HomeController {

	protected static final LoggerWrapper LOG = LoggerWrapper.get(HomeController.class);

	@RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		model.addAttribute("message", "Hello from Controller!");
		LOG.info("<------ Home page is Loaded " + (new Date().toString()));
		return "home";
	}


	//==================== LOGIN + LOGOUT + DENIED ==============================

	@RequestMapping(value = "/login")
	public String login(@RequestParam(value = "error", required = false) String error,
						ModelMap model){
		if (error != null) {
			model.addAttribute("loginError", "Invalid username or password!");
		}
		return "login";
	}


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
//		model.addAttribute("user_roles", getRoles());
//		model.addAttribute("user_name", getUserName());
		return "accessDenied";
	}




}