package ua.kiev.toolstore.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kiev.toolstore.model.Product;
import ua.kiev.toolstore.repository.impl.ProductDaoCollection;
import ua.kiev.toolstore.services.ProductService;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDaoCollection productDaoCollection;

    public List<Product> findAllProducts() {
        return productDaoCollection.findAllProducts();
    }

    public Product findById(Long id) {
        return productDaoCollection.findById(id);
    }

    public void saveProduct(Product product) {
        productDaoCollection.saveProduct(product);
    }

    public void updateProduct(Product product) {
        productDaoCollection.updateProduct(product);
    }

    public void deleteProductById(Long id) {
        productDaoCollection.deleteProductById(id);
    }
}
