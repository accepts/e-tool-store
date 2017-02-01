package ua.kiev.toolstore.services;

import ua.kiev.toolstore.model.User;

public interface UserService {

    //User findById(Long id);

    void save(User user);

    void delete(Long id);

    User saveAndFlush(User user);
}
