package ua.kiev.toolstore.services;


import ua.kiev.toolstore.model.security.User;

import java.util.List;

public interface UserService {

    User findById(Long id);

    long countByEmail(String email);

    List<User> findAll();

    void save(User user);

    void resetUser(Long id, boolean value);

    void delete(Long id);

}
