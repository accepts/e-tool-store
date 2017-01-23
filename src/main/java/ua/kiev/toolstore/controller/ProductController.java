package ua.kiev.toolstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.kiev.toolstore.model.Product;
import ua.kiev.toolstore.model.enums.ProductCategory;
import ua.kiev.toolstore.model.enums.ProductCondition;
import ua.kiev.toolstore.model.enums.ProductStatus;
import ua.kiev.toolstore.repository.dummyrepo.DummyRepo;
import ua.kiev.toolstore.util.LoggerWrapper;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
public class ProductController {

    protected static final LoggerWrapper LOG = LoggerWrapper.get(ProductController.class);


    @Autowired
    private DummyRepo repository;


    // ----------------------------  Enum values for Thymeleaf view ---------------------------
    @ModelAttribute("allProductCategory")
    public List<ProductCategory> populateProductCategory() {
        return Arrays.asList(ProductCategory.ALL);
    }

    @ModelAttribute("allProductConditions")
    public List<ProductCondition> populateProductCondition() {
        return Arrays.asList(ProductCondition.ALL);
    }

    @ModelAttribute("allProductStatus")
    public List<ProductStatus> populateProductStatus() {
        return Arrays.asList(ProductStatus.ALL);
    }

    @ModelAttribute("allProducts")
    public List<Product> populateProduct() {
        return this.repository.findAllProducts();
    }
    //  -----------------------------------------------------------------------------------------



    @RequestMapping(value = "/createProduct")
    public String createProduct(Product product){
        return "createProduct";
    }

    @RequestMapping(value = "/createProduct", params = {"save"})
    public String saveProduct(Product product, BindingResult bindingResult, ModelMap model){
        if (bindingResult.hasErrors()) {
            return "/createProduct";
        }
//        else {
//            if (repository.countByLastName(person.getLastName()) > 0){
//                bindingResult.reject("error.person.firstName.dublicate");
//                return "/createProduct";
//            }
            repository.saveProduct(product);
            model.clear();
            return "redirect:/createProduct";
        }


	/*  ------------------------------ Add + Remove rows in attributes MAP -----------------------------------------   */

    @RequestMapping(value = "/createProduct", params = {"addRow"})
    public  String addRow(Product product, BindingResult bindingResult){
        product.getAttributes().put("<Key>","<Value>");
        LOG.info("<---!!!---Test addRow {}", product.getAttributes());
        return "createProduct";
    }

    @RequestMapping(value = "/createProduct", params = {"removeRow"})
    public  String removeRow(Product product, BindingResult bindingResult, HttpServletRequest req){
        String rowId = req.getParameter("removeRow");
        product.getAttributes().remove(rowId);
        return "createProduct";
    }



/*
    @RequestMapping(value = "/personCreate", params={"addRow"})
    public String addRow(Person person, BindingResult bindingResult){
        person.getAddresses().add(new Address("Country","City","Street"));
        return "personCreate";
    }

    @RequestMapping(value = "/personCreate", params={"removeRow"})
    public String removeRow(Person person, BindingResult bindingResult, HttpServletRequest req){
        final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
        person.getAddresses().remove(rowId.intValue());
        return "personCreate";
    }


    */




}
