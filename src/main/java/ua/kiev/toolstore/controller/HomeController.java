package ua.kiev.toolstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.kiev.toolstore.util.LoggerWrapper;

@Controller
public class HomeController {

	protected static final LoggerWrapper LOG = LoggerWrapper.get(HomeController.class);

	@RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		model.addAttribute("message", "Hello from Controller!");
		LOG.info("<------ Logging is testing {}");
		return "home";
	}








}