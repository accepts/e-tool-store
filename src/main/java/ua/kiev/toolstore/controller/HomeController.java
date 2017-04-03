package ua.kiev.toolstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.kiev.toolstore.util.LoggerWrapper;
import ua.kiev.toolstore.util.exceptions.CustomGenericException;

import java.util.Date;

@Controller
public class HomeController {

    protected static final LoggerWrapper LOG = LoggerWrapper.get(HomeController.class);

// Use this method if you want automatically generate product-menu on nav-bar
//	@ModelAttribute("allProductCategory")
//	public List<ProductCategory> populateProductCategory() {
//		return Arrays.asList(ProductCategory.ALL);
//	}


    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {
        model.addAttribute("message", "Hello from Controller!");
        LOG.info("<------ Home page is Loaded " + (new Date().toString()));

        return "home";
    }

    @RequestMapping(value = "/error")
    public String errorHandle() {
        throw new CustomGenericException("E888", "Current address does not Exist!");
    }


}