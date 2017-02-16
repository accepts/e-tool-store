package ua.kiev.toolstore.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;
import ua.kiev.toolstore.model.Product;
import ua.kiev.toolstore.model.enums.ProductCategory;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findAll();

    Product findById(Long id);

    //long count();

    //long countByLastName(String lastName);

    @Async
    @Query(value = "SELECT p.picture FROM products p WHERE p.id = ?1", nativeQuery = true)
    String findPictureByProductId(Long id);

//    @Query("SELECT p FROM Products p WHERE p.condition = 'title'")
    List<Product> findByCategory(ProductCategory category);



}
