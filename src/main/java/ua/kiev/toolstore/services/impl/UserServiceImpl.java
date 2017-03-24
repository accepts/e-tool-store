package ua.kiev.toolstore.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.kiev.toolstore.model.enums.Role;
import ua.kiev.toolstore.model.security.AuthorizedUser;
import ua.kiev.toolstore.model.security.User;
import ua.kiev.toolstore.repository.UserRepository;
import ua.kiev.toolstore.services.UserService;

import java.util.EnumSet;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    @Value("${product.item.per.page.admin}")
    private int PAGE_SIZE_ADMIN;

    @Autowired
    private UserRepository repository;

    public User findById(Long id) {
        return repository.findById(id);
    }

    public long countByEmail(String email) {
        return repository.countByEmail(email);
    }

    public Page<User> findAll(Integer pageNumber) {
        PageRequest request = new PageRequest(pageNumber, PAGE_SIZE_ADMIN);
        return repository.findAllByOrderByIdAsc(request);
    }

    public void save(User user) {
        if (user.getRoles() == null || user.getRoles().isEmpty()){
            user.setRoles(EnumSet.of(Role.ROLE_CUSTOMER));
        }
        repository.save(user);
    }

    public void resetUser(Long id, boolean value) {
        repository.resetUser(id, value);
    }


    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    }


    public void delete(Long id){
        repository.delete(id);
    }
}
