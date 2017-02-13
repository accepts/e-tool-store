package ua.kiev.toolstore.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ua.kiev.toolstore.model.security.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    User findById(Long id);

    long countByEmail(String email);

    List<User> findAllByOrderByIdAsc();
//    List<User> findAll();

    User findByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "UPDATE users SET enabled = ?2 WHERE id=?1", nativeQuery = true)
    void resetUser(Long id, boolean value);

//    @Query(value = "SELECT p.picture FROM products p WHERE p.id = ?1", nativeQuery = true)
//    String findPictureByProductId(Long id);

//    UPDATE users SET enabled = false WHERE id=3;
}
