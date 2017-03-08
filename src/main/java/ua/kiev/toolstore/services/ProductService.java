package ua.kiev.toolstore.services;

import org.springframework.data.domain.Page;
import ua.kiev.toolstore.model.Product;
import ua.kiev.toolstore.model.enums.ProductCategory;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product findById(Long id);

    void save(Product product);

    void delete(Long id);

    String findPictureByProductId(Long id);

    List<Product> findByCategory(ProductCategory category);

    Page<Product> findAll(Integer pageNumber);

    Page<Product> findProductByStatus(String status, Integer pageNumber);

    Page<Product> switchProductStatus(String status, Long orderId, String action, Integer pageNumber);

    void setUnitInStock(Long id, int unitInStock);



}








