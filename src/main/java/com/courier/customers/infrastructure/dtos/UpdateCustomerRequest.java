package com.courier.customers.infrastructure.dtos;

import com.courier.customers.domain.models.CustomerRole;
import lombok.Data;

@Data
public class UpdateCustomerRequest {
    private String name;
    private CustomerRole role;
}
