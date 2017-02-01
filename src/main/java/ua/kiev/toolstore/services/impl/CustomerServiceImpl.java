package ua.kiev.toolstore.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kiev.toolstore.model.Customer;
import ua.kiev.toolstore.repository.CustomerRepository;
import ua.kiev.toolstore.services.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository repository;

    public void save(Customer customer) {
        repository.save(customer);
    }

    public Customer saveAndFlush(Customer customer) {
        return repository.saveAndFlush(customer);
    }


//    public List<Customer> findAll() {
//        return repository.findAll();
//    }
//
//    public Customer findById(Long id) {
//        return repository.findById(id);
//    }
//
//    public void save(Customer customer) {
//        repository.save(customer);
//    }
//
//    public void delete(Long id) {
//        repository.delete(id);
//    }
}
