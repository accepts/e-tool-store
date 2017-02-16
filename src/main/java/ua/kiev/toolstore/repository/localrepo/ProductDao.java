package ua.kiev.toolstore.repository.localrepo;

import ua.kiev.toolstore.model.Product;

import java.util.List;

public interface ProductDao {

    List<Product> findAll();

    Product findById(Long id);

    void save(Product product);

//    void update(Product product);

    void delete(Long id);

}
