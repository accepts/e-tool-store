package ua.kiev.toolstore.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kiev.toolstore.model.Product;
import ua.kiev.toolstore.repository.ProductDao;
import ua.kiev.toolstore.services.ProductService;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao repository;
//    private ProductRepository repository;

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Product findById(Long id) {
        return repository.findById(id);
    }

    public void save(Product product) {
        repository.save(product);
    }

    //public void updateProduct(Product product) {
//        repository.save(product);
//    }

    public void delete(Long id) {
        repository.delete(id);
    }
}
