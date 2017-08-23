package ua.kiev.toolstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;
import ua.kiev.toolstore.model.Product;
import ua.kiev.toolstore.model.enums.ProductCategory;
import ua.kiev.toolstore.model.enums.ProductStatus;

import java.util.Collection;
import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findTop3AllByOrderByIdDesc();

    Product findById(Long id);

    @Async
    @Query(value = "SELECT p.picture FROM products p WHERE p.id = ?1", nativeQuery = true)
    String findPictureByProductId(Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE products SET unitinstock = ?2 WHERE id= ?1", nativeQuery = true)
    void setUnitInStock(Long id, int unitInStock);

    @Transactional
    @Modifying
    @Query(value = "UPDATE products p SET status = ?2 WHERE p.id = ?1", nativeQuery = true)
    void changeStatus(Long productId, String status);

    Page<Product> findAllByOrderByIdDesc(Pageable pageable);

    Page<Product> findByStatus(ProductStatus status, Pageable pageable);

    Page<Product> findByStatusNotIn(Collection<ProductStatus> status, Pageable pageable);

    Page<Product> findByCategoryAndStatusNotIn(ProductCategory category, Collection<ProductStatus> status, Pageable pageable);

/*
    @Query(value = "SELECT * FROM products p WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
            "LOWER(p.manufacturer) LIKE LOWER(CONCAT('%',:searchTerm, '%'))",
            nativeQuery = true
    )
    List<Product> findBySearchTermNative(@Param("searchTerm") String searchTerm);
*/
    //searchTerm = name, description, manufacturer
    Page<Product> findAllByNameIgnoreCaseContainingOrDescriptionIgnoreCaseContainingOrManufacturerIgnoreCaseContainingAndStatusNotIn(String searchTerm, String searchTerm2, String searchTerm3, Collection<ProductStatus> status, Pageable pageable);

}
