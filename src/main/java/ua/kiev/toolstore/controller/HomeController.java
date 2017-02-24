package ua.kiev.toolstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.kiev.toolstore.model.enums.ProductCategory;
import ua.kiev.toolstore.util.LoggerWrapper;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
public class HomeController {

	protected static final LoggerWrapper LOG = LoggerWrapper.get(HomeController.class);


	@ModelAttribute("allProductCategory")
	public List<ProductCategory> populateProductCategory() {
		return Arrays.asList(ProductCategory.ALL);
	}


	@RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		model.addAttribute("message", "Hello from Controller!");
		LOG.info("<------ Home page is Loaded " + (new Date().toString()));
		return "home";
	}

}