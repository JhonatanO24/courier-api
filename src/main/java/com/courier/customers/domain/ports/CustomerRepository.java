package com.courier.customers.domain.ports;

import com.courier.customers.domain.models.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer save(Customer customer);
    Optional<Customer> findById(UUID id);
    Optional<Customer> findByEmail(String email);
    List<Customer> findAll();
    boolean existsByEmail(String email);
}
