package ua.kiev.toolstore.repository;

import org.springframework.data.repository.CrudRepository;
import ua.kiev.toolstore.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

    //User findById(Long id);

    long countByEmail(String email);

}
