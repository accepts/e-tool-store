package ua.kiev.toolstore.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ua.kiev.toolstore.model.Product;
import ua.kiev.toolstore.model.enums.ProductCategory;
import ua.kiev.toolstore.repository.ProductRepository;
import ua.kiev.toolstore.services.ProductService;
import ua.kiev.toolstore.util.FileManager;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private FileManager fileManager;

    @Value("${product.item.per.page}")
    private int PAGE_SIZE;


    public List<Product> findAll() {
        return repository.findAll();
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


    public List<Product> findByCategory(ProductCategory category) {
        return repository.findByCategory(category);
    }

    public Page<Product> findAll(Integer pageNumber) {
//        PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE);
        PageRequest request = new PageRequest(pageNumber, PAGE_SIZE);
        return repository.findAll(request);
    }
}
