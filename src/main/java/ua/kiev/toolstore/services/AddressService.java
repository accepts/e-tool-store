package ua.kiev.toolstore.services;

import ua.kiev.toolstore.model.Address;

public interface AddressService {

    void save(Address address);

    Address saveAndFlush(Address address);

    void delete(Long id);

    Address findById(Long id);

}
