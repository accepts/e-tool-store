package ua.kiev.toolstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.kiev.toolstore.model.enums.ProductCategory;
import ua.kiev.toolstore.services.OrderService;
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










	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/order/add/dummy/{productId}")
	public String addProductToOrder(@PathVariable Long productId){
		LOG.debug("<---ORDER-SERV-CONTROLLER ---- START...");
		orderService.save(productId);
		LOG.debug("<---ORDER-SERV-CONTROLLER ---- END!");
		return "redirect:/home";
	}



}