package ua.kiev.toolstore.services;

import ua.kiev.toolstore.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAllProducts();

    Product findById(Long id);

    void saveProduct(Product product);

    void updateProduct(Product product);

    void deleteProductById(Long id);

}
