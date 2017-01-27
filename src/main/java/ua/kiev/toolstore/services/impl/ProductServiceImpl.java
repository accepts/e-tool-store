package ua.kiev.toolstore.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kiev.toolstore.model.Product;
import ua.kiev.toolstore.repository.ProductRepository;
import ua.kiev.toolstore.services.ProductService;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;
    //    private ProductDao repository;


    public List<Product> findAll() {
        return repository.findAll();
    }

    public Product findById(Long id) {
        return repository.findById(id);
    }

    public void save(Product product) {
        repository.save(product);
    }

    public void delete(Long id) {
        //TODO delete file when deleting picture
        repository.delete(id);
    }

    public String findPictureByProductId(Long id) {
        return repository.findPictureByProductId(id);
    }
}
