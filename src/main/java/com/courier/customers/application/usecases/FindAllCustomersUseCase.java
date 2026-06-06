package com.courier.customers.application.usecases;

import com.courier.customers.domain.models.Customer;
import com.courier.customers.domain.ports.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindAllCustomersUseCase {

    private final CustomerRepository customerRepository;

    public FindAllCustomersUseCase(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> execute() {
        return customerRepository.findAll();
    }
}
