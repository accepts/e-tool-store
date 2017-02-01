package ua.kiev.toolstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kiev.toolstore.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

//    List<Customer> findAll();
//
//    Customer findById(Long id);
}
