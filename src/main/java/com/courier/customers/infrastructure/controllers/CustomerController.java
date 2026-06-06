package com.courier.customers.infrastructure.controllers;

import com.courier.customers.application.usecases.*;
import com.courier.customers.infrastructure.dtos.CreateCustomerRequest;
import com.courier.customers.infrastructure.dtos.CustomerResponse;
import com.courier.customers.infrastructure.dtos.UpdateCustomerRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
@Tag(name = "Customers", description = "Customer management")
public class CustomerController {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final FindAllCustomersUseCase findAllCustomersUseCase;
    private final FindCustomerByIdUseCase findCustomerByIdUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final DeleteCustomerUseCase deleteCustomerUseCase;

    public CustomerController(
            CreateCustomerUseCase createCustomerUseCase,
            FindAllCustomersUseCase findAllCustomersUseCase,
            FindCustomerByIdUseCase findCustomerByIdUseCase,
            UpdateCustomerUseCase updateCustomerUseCase,
            DeleteCustomerUseCase deleteCustomerUseCase
    ) {
        this.createCustomerUseCase = createCustomerUseCase;
        this.findAllCustomersUseCase = findAllCustomersUseCase;
        this.findCustomerByIdUseCase = findCustomerByIdUseCase;
        this.updateCustomerUseCase = updateCustomerUseCase;
        this.deleteCustomerUseCase = deleteCustomerUseCase;
    }

    @PostMapping
    @Operation(summary = "Create a new customer")
    public ResponseEntity<CustomerResponse> create(@Valid @RequestBody CreateCustomerRequest request) {
        var customer = createCustomerUseCase.execute(
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getRole()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(CustomerResponse.from(customer));
    }

    @GetMapping
    @Operation(summary = "List all customers")
    public ResponseEntity<List<CustomerResponse>> findAll() {
        var customers = findAllCustomersUseCase.execute()
                .stream()
                .map(CustomerResponse::from)
                .toList();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find customer by ID")
    public ResponseEntity<CustomerResponse> findById(@PathVariable UUID id) {
        var customer = findCustomerByIdUseCase.execute(id);
        return ResponseEntity.ok(CustomerResponse.from(customer));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update customer name or role")
    public ResponseEntity<CustomerResponse> update(
            @PathVariable UUID id,
            @RequestBody UpdateCustomerRequest request
    ) {
        var customer = updateCustomerUseCase.execute(id, request.getName(), request.getRole());
        return ResponseEntity.ok(CustomerResponse.from(customer));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deactivate customer (soft delete)")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteCustomerUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
