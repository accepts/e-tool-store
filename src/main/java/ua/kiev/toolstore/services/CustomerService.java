package ua.kiev.toolstore.services;

import ua.kiev.toolstore.model.Customer;

public interface CustomerService {

//    List<Customer> findAll();

//    Customer findById(Long id);

    void save(Customer customer);

    Customer saveAndFlush(Customer customer);

//    void delete(Long id);

}
