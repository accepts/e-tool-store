package ua.kiev.toolstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.kiev.toolstore.model.Feature;
import ua.kiev.toolstore.model.Product;
import ua.kiev.toolstore.model.enums.ProductCategory;
import ua.kiev.toolstore.model.enums.ProductCondition;
import ua.kiev.toolstore.model.enums.ProductStatus;
import ua.kiev.toolstore.services.ProductService;
import ua.kiev.toolstore.util.FileManager;
import ua.kiev.toolstore.util.LoggerWrapper;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value = "/product")
public class ProductController {

    protected static final LoggerWrapper LOG = LoggerWrapper.get(ProductController.class);


    @Autowired
    private ProductService productService;

    @Autowired
    private FileManager fileManager;

    // --------------------  Enum values necessary for Thymeleaf view -----------------------
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
        return this.productService.findAll();
    }
    //  ---------------------------------------------------------------------------------------



    @RequestMapping(value = "/create")
    public String createProduct(Product product){
        product.setCondition(ProductCondition.NEW);
        return "productCreate";
    }


    //  **************************** CREATE Product ****************************
    @RequestMapping(value = "/create", params = {"save"})
    public String saveProduct(Product product, BindingResult bindingResult, ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "productCreate";
        }
//        else {
//            if (productService.countByLastName(person.getLastName()) > 0){
//                bindingResult.reject("error.person.firstName.dublicate");
//                return "/createProduct";
//            }
            if (product.getId() != null){
                LOG.info("<===!!!!=========Edit product {}", product);
                //TODO operation with picture
                productService.save(product);
                model.clear();
            } else {

                //MultipartFile uploadedImage = product.getProductImage();

                String picture = fileManager.saveFileToLocalStorage(product.getProductImage());
                if (picture != null){
                    product.setPicture(picture);
                }



               // LOG.debug("<------ TRansfere file to: " + fileDest);
                productService.save(product);
                LOG.info("<-------Save product: {}", product);
                model.clear();
            }

            return "redirect:/product/create";
        }

	/*  ----Add + Remove rows in List<features> when CREATE Product ---   */
    @RequestMapping(value = "/create", params = {"addRow"})
    public  String addRow(Product product, BindingResult bindingResult){
        product.getFeatures().add(new Feature("<Title>", "<Body>", "<Attribute>"));
        LOG.info("<------Test addRow {}", product.getFeatures());
        return "productCreate";
    }

    @RequestMapping(value = "/create", params = {"removeRow"})
    public  String removeRow(Product product, BindingResult bindingResult, HttpServletRequest req){
        final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
        product.getFeatures().remove(rowId.intValue());
        return "productCreate";
    }


    //  **************************** DELETE Product ****************************
    @RequestMapping(value = "/delete/{id}")
    public String deleteProduct(@PathVariable Long id, Product product){
        productService.delete(id);
        LOG.info("<------Delete product with ID: || " + id);
        return "productCreate";
    }


    //  **************************** EDIT Product ****************************
    @RequestMapping(value = "/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model){
        Product product = productService.findById(id);
        model.addAttribute(product);
        LOG.info("<------Edit product {}", product);
        return "productCreate";
    }

    //  **************************** VIEW Product ****************************
    @RequestMapping(value = "/view/{id}")
    public String getProductById(@PathVariable Long id, Model model){
        model.addAttribute("productDetailed", productService.findById(id));
        return "productDetail";
    }

}
