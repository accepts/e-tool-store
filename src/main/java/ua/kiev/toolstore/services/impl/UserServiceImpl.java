package ua.kiev.toolstore.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.kiev.toolstore.model.security.AuthorizedUser;
import ua.kiev.toolstore.model.security.User;
import ua.kiev.toolstore.repository.UserRepository;
import ua.kiev.toolstore.services.UserService;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository repository;

    public User findById(Long id) {
        return repository.findById(id);
    }

    public long countByEmail(String email) {
        return repository.countByEmail(email);
    }

    public List<User> findAll() {
        return repository.findAllByOrderByIdAsc();
    }

    public void save(User user) {
        repository.save(user);
    }

    public void resetUser(Long id, boolean value) {
        repository.resetUser(id, value);
    }

    //http://www.ekiras.com/2016/04/authenticate-user-with-custom-user-details-service-in-spring-security.html
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    }



/*
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), user.getRoles());
    }
*/


}