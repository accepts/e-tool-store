package ua.kiev.toolstore.services;

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

}
