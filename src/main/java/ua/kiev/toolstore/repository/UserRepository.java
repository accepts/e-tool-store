package ua.kiev.toolstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ua.kiev.toolstore.model.security.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    User findById(Long id);

    long countByEmail(String email);

//    List<User> findAllByOrderByIdAsc();

    Page<User> findAllByOrderByIdAsc(Pageable pageable);

    User findByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "UPDATE users SET enabled = ?2 WHERE id=?1", nativeQuery = true)
    void resetUser(Long id, boolean value);

    void delete(Long id);

}
