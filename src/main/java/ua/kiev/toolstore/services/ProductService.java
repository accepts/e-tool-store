package ua.kiev.toolstore.services;

import ua.kiev.toolstore.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product findById(Long id);

    void save(Product product);

//    void updateProduct(Product product);

    void delete(Long id);

}
