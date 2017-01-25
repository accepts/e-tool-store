package ua.kiev.toolstore.repository;

import org.springframework.data.repository.CrudRepository;
import ua.kiev.toolstore.model.Product;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findAll();

    Product findById(Long id);

    //long count();

    //long countByLastName(String lastName);
}
