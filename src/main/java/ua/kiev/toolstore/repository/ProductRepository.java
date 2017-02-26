package ua.kiev.toolstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;
import ua.kiev.toolstore.model.Product;
import ua.kiev.toolstore.model.enums.ProductCategory;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findAllByOrderByIdAsc();

    Page<Product> findAllByOrderByIdAsc(Pageable pageable);

    Product findById(Long id);

    List<Product> findByCategory(ProductCategory category);

    @Async
    @Query(value = "SELECT p.picture FROM products p WHERE p.id = ?1", nativeQuery = true)
    String findPictureByProductId(Long id);


    @Transactional
    @Modifying
    @Query(value = "UPDATE products SET unitinstock = ?2 WHERE id= ?1", nativeQuery = true)
    void setUnitInStock(Long id, int unitInStock);



    //long count();
    //long countByLastName(String lastName);

}
