package ua.kiev.toolstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kiev.toolstore.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Address findById(Long id);

}
