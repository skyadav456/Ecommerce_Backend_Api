package com.sharad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sharad.entity.Address;
import java.util.List;

public interface AddressRepository  extends JpaRepository<Address, Long> {

    List<Address> findByCustomer_CustomerId(Long customerId);
}
