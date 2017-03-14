package ua.kiev.toolstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
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



    // -------------------- Browse Product All and by Category ---------------------
    @RequestMapping(value = "/{category}/page/{pageNumber}")
    public String getProductList(@PathVariable String category,
                                 @PathVariable Integer pageNumber,
                                 @RequestParam(value = "orderBy", required = false, defaultValue = "price") Optional<String> orderBy,
                                 @RequestParam(value = "sortBy", required = false, defaultValue = "asc") Optional<String> sortBy,
                                 ModelMap model){
        if (category == null || category.trim().isEmpty()){
            category = "all";
        }
        if (pageNumber == null){
            pageNumber = 0;
        }

        model.addAttribute("productsPage", productService.findProductByCategory(category, pageNumber,  orderBy, sortBy))
                .addAttribute("productCategory", category)
                .addAttribute("orderBy", orderBy.get())
                .addAttribute("sortBy", sortBy.get());

        return "productList";
    }



    // ---------------- Admin Product  manager page (Change ProductStatus, DELETE) --------------------------
    @RequestMapping(value = {"/admin/manage/{status}/page/{pageNumber}",
            "/admin/manage/{status}/page/{pageNumber}/{action}/{id}"})
    public String getProductsForManage(@PathVariable(value = "status") String status,
                                       @PathVariable(value = "pageNumber") Integer pageNumber,
                                       @PathVariable(value = "action") Optional<String> action,
                                       @PathVariable(value = "id") Optional<Long> id,
                                       ModelMap model) throws IllegalArgumentException{

        if (action.isPresent() && id.isPresent()){
            model.addAttribute("productsPage", productService.switchProductStatus(status, id.get(), action.get(), pageNumber));
        } else {
            model.addAttribute("productsPage", productService.findProductByStatus(status, pageNumber));
        }
        model.addAttribute("productStatus", status);
        return "productManager";
    }



    @RequestMapping(value = "/search", method = RequestMethod.POST, params = {"startSearch"})
    public String  searchProduct(ModelMap model, HttpServletRequest req) {
        String searchTerm = String.valueOf(req.getParameter("searchTerm"));
        model.addAttribute("productsPage", productService.findProduct(searchTerm, 0))
                .addAttribute("searchTerm", searchTerm);
        return "searchResult";
    }



    @RequestMapping(value = "/search/page/{pageNumber}", method = RequestMethod.GET)
    public String  searchProductBrowse(ModelMap model,
                                       @PathVariable(value = "pageNumber") Integer pageNumber,
                                       @RequestParam(value = "searchTerm", required = false) Optional<String> searchTerm) {
        if (!searchTerm.isPresent()){
            return "redirect:/home";
        }

        model.addAttribute("productsPage", productService.findProduct(searchTerm.get(), pageNumber))
                .addAttribute("searchTerm", searchTerm);
        return "searchResult";
    }


}
