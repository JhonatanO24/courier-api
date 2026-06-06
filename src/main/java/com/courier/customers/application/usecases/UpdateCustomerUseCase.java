package com.courier.customers.application.usecases;

import com.courier.customers.domain.exceptions.CustomerNotFoundException;
import com.courier.customers.domain.models.Customer;
import com.courier.customers.domain.models.CustomerRole;
import com.courier.customers.domain.ports.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UpdateCustomerUseCase {

    private final CustomerRepository customerRepository;

    public UpdateCustomerUseCase(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer execute(UUID id, String name, CustomerRole role) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));

        if (name != null && !name.isBlank()) {
            customer.setName(name);
        }
        if (role != null) {
            customer.setRole(role);
        }
        customer.setUpdatedAt(LocalDateTime.now());

        return customerRepository.save(customer);
    }
}
