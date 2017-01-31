package ua.kiev.toolstore.services;

import ua.kiev.toolstore.model.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> findAll();

    Customer findById(Long id);

    void save(Customer customer);

    void delete(Long id);

}
