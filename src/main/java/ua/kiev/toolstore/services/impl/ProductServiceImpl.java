package ua.kiev.toolstore.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ua.kiev.toolstore.model.Product;
import ua.kiev.toolstore.model.enums.ProductCategory;
import ua.kiev.toolstore.model.enums.ProductStatus;
import ua.kiev.toolstore.repository.LineItemRepository;
import ua.kiev.toolstore.repository.ProductRepository;
import ua.kiev.toolstore.services.ProductService;
import ua.kiev.toolstore.util.FileManager;
import ua.kiev.toolstore.util.LoggerWrapper;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    protected static final LoggerWrapper LOG = LoggerWrapper.get(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private LineItemRepository lineItemRepository;

    @Autowired
    private FileManager fileManager;

    @Value("${product.item.per.page}")
    private int PAGE_SIZE;

    @Value("${product.item.per.page.admin}")
    private int PAGE_SIZE_ADMIN;

    @Value("${product.search.sort.by}")
    private String SEARCH_SORT;


    public List<Product> findAll() {
        return productRepository.findTop3AllByOrderByIdDesc();
    }


    public Product findById(Long id) {
        return productRepository.findById(id);
    }


    public void save(Product product) {
        productRepository.save(product);
    }

    @Transactional
    public void delete(Long id) {
        Assert.isTrue(fileManager.deleteFile(id), "Can't delete file from LOCAL storage!");
        lineItemRepository.deleteItemWithProductId(id);
        productRepository.delete(id);
        LOG.info("<---DELETE Product from repository productId: " + id);
    }

    public String findPictureByProductId(Long id) {
        return productRepository.findPictureByProductId(id);
    }


    public void setUnitInStock(Long id, int unitInStock) {
        productRepository.setUnitInStock(id, unitInStock);
    }


    public Page<Product> findProductByCategory(String category, Integer pageNumber, Optional<String> orderBy, Optional<String> sortBy){
        PageRequest request = new PageRequest(pageNumber, PAGE_SIZE, new Sort(Sort.Direction.valueOf(sortBy.get().toUpperCase()), orderBy.get()));;

        if (category.equalsIgnoreCase("all")){
            return productRepository.findByStatusNotIn(EnumSet.of(ProductStatus.LOCKED, ProductStatus.OBSOLETE), request);
        }

        return productRepository.findByCategoryAndStatusNotIn(ProductCategory.valueOf(category.toUpperCase()),
                EnumSet.of(ProductStatus.LOCKED, ProductStatus.OBSOLETE), request);
    }


    // ----- Search user "Therm" method -----------
    public Page<Product> findProduct(String searchTerm, Integer pageNumber){
        PageRequest request = new PageRequest(pageNumber, PAGE_SIZE, new Sort(Sort.Direction.ASC, SEARCH_SORT));

        return productRepository.findAllByNameIgnoreCaseContainingOrDescriptionIgnoreCaseContainingOrManufacturerIgnoreCaseContainingAndStatusNotIn(
                searchTerm, searchTerm, searchTerm, EnumSet.of(ProductStatus.LOCKED, ProductStatus.OBSOLETE), request);
    }



    // *****************************  For ADMIN purposes ********************************************

    public Page<Product> findProductByStatus(String status, Integer pageNumber) throws IllegalArgumentException{
        PageRequest request = new PageRequest(pageNumber, PAGE_SIZE_ADMIN);
        if (status.equalsIgnoreCase("all")){
            return productRepository.findAllByOrderByIdDesc(request);
        }
        return productRepository.findByStatus(ProductStatus.valueOf(status.toUpperCase()), request);
    }


    @Transactional
    public Page<Product> switchProductStatus(String status, Long productId,
                                         String action, Integer pageNumber) throws IllegalArgumentException{
        PageRequest request = null;

        if (action.equalsIgnoreCase("delete")){
            delete(productId);
        } else{
            ProductStatus newStatus = ProductStatus.valueOf(action.toUpperCase());
            productRepository.changeStatus(productId, newStatus.toString());
            request = new PageRequest(pageNumber, PAGE_SIZE_ADMIN);
        }

        if (status.equalsIgnoreCase("all")){
            return productRepository.findAllByOrderByIdDesc(request);
        }

        return productRepository.findByStatus(ProductStatus.valueOf(status.toUpperCase()), request);
    }

}
