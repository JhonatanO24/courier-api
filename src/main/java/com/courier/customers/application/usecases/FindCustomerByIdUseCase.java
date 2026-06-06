package com.courier.customers.application.usecases;

import com.courier.customers.domain.exceptions.CustomerNotFoundException;
import com.courier.customers.domain.models.Customer;
import com.courier.customers.domain.ports.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FindCustomerByIdUseCase {

    private final CustomerRepository customerRepository;

    public FindCustomerByIdUseCase(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer execute(UUID id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }
}
