package ua.kiev.toolstore.repository;

import org.springframework.data.repository.CrudRepository;
import ua.kiev.toolstore.model.Customer;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findAll();

    Customer findById(Long id);
}
