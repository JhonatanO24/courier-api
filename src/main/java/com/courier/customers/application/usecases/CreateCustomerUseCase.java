package com.courier.customers.application.usecases;

import com.courier.customers.domain.exceptions.EmailAlreadyExistsException;
import com.courier.customers.domain.models.Customer;
import com.courier.customers.domain.models.CustomerRole;
import com.courier.customers.domain.ports.CustomerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CreateCustomerUseCase {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public CreateCustomerUseCase(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public Customer execute(String name, String email, String password, CustomerRole role) {
        if (customerRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException(email);
        }

        Customer customer = new Customer(
                null,
                name,
                email,
                passwordEncoder.encode(password),
                role != null ? role : CustomerRole.SENDER,
                true,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        return customerRepository.save(customer);
    }
}
