package com.courier.customers.application.usecases;

import com.courier.customers.domain.exceptions.CustomerNotFoundException;
import com.courier.customers.domain.models.Customer;
import com.courier.customers.domain.ports.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class DeleteCustomerUseCase {

    private final CustomerRepository customerRepository;

    public DeleteCustomerUseCase(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void execute(UUID id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));

        customer.setActive(false);
        customer.setUpdatedAt(LocalDateTime.now());
        customerRepository.save(customer);
    }
}
