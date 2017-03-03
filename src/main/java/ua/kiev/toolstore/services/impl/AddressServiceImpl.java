package ua.kiev.toolstore.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kiev.toolstore.model.Address;
import ua.kiev.toolstore.repository.AddressRepository;
import ua.kiev.toolstore.services.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository repository;

    public void save(Address address) {
        repository.save(address);
    }

    public Address saveAndFlush(Address address) {
        return repository.saveAndFlush(address);
    }

    public void delete(Long id) {
        repository.delete(id);
    }

    public Address findById(Long id) {
        return repository.findById(id);
    }
}
