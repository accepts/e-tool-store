package ua.kiev.toolstore.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kiev.toolstore.model.User;
import ua.kiev.toolstore.repository.tempDao.UserRepository;
import ua.kiev.toolstore.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

//    public User findById(Long id) {
//        return repository.findById(id);
//    }

    public void save(User user) {
        repository.save(user);
    }

    public void delete(Long id) {
        repository.delete(id);
    }

    public User saveAndFlush(User user) {
        return repository.saveAndFlush(user);
    }


}
