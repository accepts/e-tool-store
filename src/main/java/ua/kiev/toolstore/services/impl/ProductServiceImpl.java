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
    private ProductRepository repository;

    @Autowired
    private FileManager fileManager;

    @Value("${product.item.per.page}")
    private int PAGE_SIZE;

    @Value("${product.item.per.page.admin}")
    private int PAGE_SIZE_ADMIN;



    public List<Product> findAll() {
        return repository.findTop3AllByOrderByIdDesc();
    }


    public Product findById(Long id) {
        return repository.findById(id);
    }


    public void save(Product product) {
        repository.save(product);
    }

    @Transactional
    public void delete(Long id) {
//        TODO this method can be used for Exception handler testing
        Assert.isTrue(fileManager.deleteFile(id), "<---E--- Can't delete file from LOCAL storage!");
        repository.delete(id);
    }

    public String findPictureByProductId(Long id) {
        return repository.findPictureByProductId(id);
    }


    public void setUnitInStock(Long id, int unitInStock) {
        repository.setUnitInStock(id, unitInStock);
    }



    public Page<Product> findProductByCategory(String category, Integer pageNumber, Optional<String> orderBy, Optional<String> sortBy){

        PageRequest request = new PageRequest(pageNumber, PAGE_SIZE, new Sort(Sort.Direction.valueOf(sortBy.get().toUpperCase()), orderBy.get()));;

        if (category.equalsIgnoreCase("all")){

            return repository.findByStatusNotIn(EnumSet.of(ProductStatus.LOCKED, ProductStatus.OBSOLETE), request);
        }

        return repository.findByCategoryAndStatusNotIn(ProductCategory.valueOf(category.toUpperCase()),
                EnumSet.of(ProductStatus.LOCKED, ProductStatus.OBSOLETE), request);
    }



    // *****************************  For ADMIN purposes ********************************************

    public Page<Product> findProductByStatus(String status, Integer pageNumber) throws IllegalArgumentException{
        PageRequest request = new PageRequest(pageNumber, PAGE_SIZE_ADMIN);
        if (status.equalsIgnoreCase("all")){
            return repository.findAllByOrderByIdDesc(request);
        }
        return repository.findByStatus(ProductStatus.valueOf(status.toUpperCase()), request);
    }


    @Transactional
    public Page<Product> switchProductStatus(String status, Long orderId,
                                         String action, Integer pageNumber) throws IllegalArgumentException{
        ProductStatus newStatus = ProductStatus.valueOf(action.toUpperCase());
        repository.changeStatus(orderId, newStatus.toString());
        PageRequest request = new PageRequest(pageNumber, PAGE_SIZE_ADMIN);

        if (status.equalsIgnoreCase("all")){
            return repository.findAllByOrderByIdDesc(request);
        }
        return repository.findByStatus(ProductStatus.valueOf(status.toUpperCase()), request);
    }








}
