package ua.kiev.toolstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.kiev.toolstore.model.Feature;
import ua.kiev.toolstore.model.Product;
import ua.kiev.toolstore.model.enums.ProductCategory;
import ua.kiev.toolstore.model.enums.ProductCondition;
import ua.kiev.toolstore.model.enums.ProductStatus;
import ua.kiev.toolstore.services.ProductService;
import ua.kiev.toolstore.util.LoggerWrapper;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
public class ProductController {

    protected static final LoggerWrapper LOG = LoggerWrapper.get(ProductController.class);


    @Autowired
    private ProductService productService;


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
        return this.productService.findAllProducts();
    }
    //  -----------------------------------------------------------------------------------------



    @RequestMapping(value = "/createProduct")
    public String createProduct(Product product){
        return "createProduct";
    }


    //  **************************** CREATE Product ****************************
    @RequestMapping(value = "/createProduct", params = {"save"})
    public String saveProduct(Product product, BindingResult bindingResult, ModelMap model){
        if (bindingResult.hasErrors()) {
            return "/createProduct";
        }
//        else {
//            if (productService.countByLastName(person.getLastName()) > 0){
//                bindingResult.reject("error.person.firstName.dublicate");
//                return "/createProduct";
//            }
            productService.saveProduct(product);
            model.clear();
            return "redirect:/createProduct";
        }

	/*  ----Add + Remove rows in List<features> when CREATE Product ---   */
    @RequestMapping(value = "/createProduct", params = {"addRow"})
    public  String addRow(Product product, BindingResult bindingResult){
        product.getFeatures().add(new Feature("<Title>", "<Body>", "<Attribute>"));
        LOG.info("<---!!!---Test addRow {}", product.getFeatures());
        return "createProduct";
    }

    @RequestMapping(value = "/createProduct", params = {"removeRow"})
    public  String removeRow(Product product, BindingResult bindingResult, HttpServletRequest req){
        final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
        product.getFeatures().remove(rowId.intValue());
        return "createProduct";
    }

    // TODO delete method
    @RequestMapping(value = "/deleteProduct/{id}")
    public String





}
