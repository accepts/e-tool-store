package ua.kiev.toolstore.services;


import org.springframework.data.domain.Page;
import ua.kiev.toolstore.model.security.User;

public interface UserService {

    User findById(Long id);

    long countByEmail(String email);

    Page<User> findAll(Integer pageNumber);

    void save(User user);

    void resetUser(Long id, boolean value);

    void delete(Long id);

}
