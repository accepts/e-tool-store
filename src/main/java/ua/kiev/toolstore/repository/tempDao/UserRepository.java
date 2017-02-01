package ua.kiev.toolstore.repository.tempDao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kiev.toolstore.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    //User findById(Long id);

}
