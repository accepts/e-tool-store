package ua.kiev.toolstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.kiev.toolstore.model.Feature;
import ua.kiev.toolstore.model.Product;
import ua.kiev.toolstore.model.enums.ProductCategory;
import ua.kiev.toolstore.model.enums.ProductCondition;
import ua.kiev.toolstore.model.enums.ProductStatus;
import ua.kiev.toolstore.services.ProductService;
import ua.kiev.toolstore.util.FileManager;
import ua.kiev.toolstore.util.LoggerWrapper;
import ua.kiev.toolstore.util.validator.ProductValidator;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/product")
public class ProductController {

    protected static final LoggerWrapper LOG = LoggerWrapper.get(ProductController.class);


    @Autowired
    private ProductService productService;

    @Autowired
    private FileManager fileManager;

    @Autowired
    private ProductValidator productValidator;

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
        return productService.findAll();
    }
    //  ---------------------------------------------------------------------------------------


    @RequestMapping(value = "/manage/create", method = {RequestMethod.GET,RequestMethod.POST})
    public String createProduct(Product product) {
        product.setCondition(ProductCondition.NEW);
        return "productCreate";
    }


    //  **************************** CREATE (+ Edit) Product ****************************

//    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/manage/create", params = {"save"})
    public String saveProduct(Product product, BindingResult bindingResult,
                              ModelMap model) throws IOException, IllegalArgumentException {

        if (bindingResult.hasErrors()) {
            return "productCreate";
        }

        // Validate Product fields
        if (!productValidator.productFieldsValidator(product)) {
            LOG.info("<-------Validation of the PRODUCT is occur! Fields has errors!");
            bindingResult.reject("validation.error.product.fields.message");
            return "productCreate";
        }

        // Validate attached picture file
        if (!product.getProductImage().isEmpty() &&
                (!productValidator.photoSizeValidate(product.getProductImage()) ||
                !productValidator.photoNameValidate(product.getProductImage()))) {

            LOG.info("<-------Validation of the photo file is occur! File is Wrong!");
            bindingResult.reject("validation.error.photo.file.message");
            return "productCreate";
        }

        // EDIT PRODUCT entity block
        if (product.getId() != null) {
            if (!product.getProductImage().isEmpty()) {
                fileManager.deleteFile(product.getId());
                String picture = fileManager.saveFileToLocalStorage(product.getProductImage());
                product.setPicture(picture);
            }
            productService.save(product);
            LOG.debug("<------Edit product {}", product);
            model.clear();
        }
        // SAVE PRODUCT entity block
        else {
            String picture = fileManager.saveFileToLocalStorage(product.getProductImage());
            if (picture != null) {
                product.setPicture(picture);
            }
            productService.save(product);
            LOG.debug("<------Save product: {}", product);
            model.clear();
        }

        return "redirect:/product/manage/create";
    }


    /*  ----Add + Remove rows in List<features> when CREATE Product ---   */
    @RequestMapping(value = "/manage/create", params = {"addRow"}, method = {RequestMethod.POST})
    public String addRow(Product product, BindingResult bindingResult) {
        product.getFeatures().add(new Feature("<Title>", "<Body>", "<Attribute>"));
        LOG.debug("<------Test addRow {}", product.getFeatures());
        return "productCreate";
    }

    @RequestMapping(value = "/manage/create", params = {"removeRow"}, method = {RequestMethod.POST})
    public String removeRow(Product product, BindingResult bindingResult, HttpServletRequest req) {
        final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
        product.getFeatures().remove(rowId.intValue());
        return "productCreate";
    }


    //  **************************** DELETE Product ****************************
    @RequestMapping(value = "/manage/delete/{id}")
    public String deleteProduct(@PathVariable Long id, Product product, ModelMap model) {
        productService.delete(id);
        LOG.debug("<------Delete product with ID: || " + id);
        model.clear();
        return "redirect:/product/create";
    }


    //  **************************** EDIT Product ****************************
    @RequestMapping(value = "/manage/edit/{id}")
    public String editProduct(@PathVariable Long id, ModelMap model) {
        Product product = productService.findById(id);
        model.addAttribute(product);
        LOG.debug("<------Edit product {}", product);
        return "productCreate";
    }


    //  **************************** VIEW Product ****************************
    @RequestMapping(value = "/detail/{id}")
    public String getProductById(@PathVariable Long id, ModelMap model) {
        model.addAttribute("productDetailed", productService.findById(id));
        return "productDetail";
    }

















    @RequestMapping(value = {"/admin/manage/{status}/page/{pageNumber}",
            "/admin/manage/{status}/page/{pageNumber}/{action}/{id}"})
    public String getProductsForManage(@PathVariable(value = "status") String status,
                                       @PathVariable(value = "pageNumber") Integer pageNumber,
                                       @PathVariable(value = "action") Optional<String> action,
                                       @PathVariable(value = "id") Optional<Long> id,
                                       ModelMap model) throws IllegalArgumentException{
        //TODO getProducts
        if (action.isPresent() && id.isPresent()){
            model.addAttribute("productsPage", productService.switchProductStatus(status, id.get(), action.get(), pageNumber));
        } else {
            model.addAttribute("productsPage", productService.findProductByStatus(status, pageNumber));
        }
        model.addAttribute("productStatus", status);
        return "productManager";
    }

















    // -------------------- Find Product by Category ---------------------
    @RequestMapping(value = "/sort/{category}")
    public String selectByCategory(@PathVariable String category, ModelMap model){
        if ("all".equals(category)){
            model.addAttribute("productsByCategory", productService.findAll());
            return "productList";
        }
        if (category.trim().isEmpty()
                || !Arrays.asList(ProductCategory.ALL).contains(ProductCategory.valueOf(category.toUpperCase()))){
            LOG.info("<---PRODUCT (SELECT CATEGORY) ERORR! {} ", category);
            return "home";
        }

        LOG.info("<---PRODUCT (SELECT CATEGORY) OK! {} ", category);
        model.addAttribute("productsByCategory", productService.findByCategory(ProductCategory.forName(category.toUpperCase())));
        return "productList";
    }


    //TODO organize this PAGEBLE method
    @RequestMapping(value = "/page/{pageNumber}")
    public String pageView(@PathVariable Integer pageNumber, ModelMap model){

        Page<Product> page = productService.findAll(pageNumber);

//        int current = page.getNumber() + 1;
//        int begin = Math.max(1, current - 2);
//        int end = Math.min(begin + 4, page.getTotalPages());

//        int current = page.getNumber() + 1;
//        int begin = Math.max(1, current - 5);
//        int end = Math.min(begin + 10, page.getTotalPages());

        model.addAttribute("productsPage", page);
        return "productListPegable";
    }



/*
ProductCategory.valueOf(category).
+++
String[] a= {"tube", "are", "fun"};
Arrays.asList(a).contains("any");
*/


    // TODO EXCEPTION HANDLER
    //  ************************** Exception handler *************************************

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleClientErrors(Exception e) {
        LOG.warn("<====E==== IllegalArgumentEXCEPTION occur {}" + e.getMessage());
//        return "redirect:/product/create";
        return "redirect:/home";
    }

    @ExceptionHandler(Exception.class)
    public String handleServerErrors(Exception e) {
        LOG.warn("<====E==== Exception occur {}" + e.getMessage());
        return "redirect:/product/create";
    }


}
