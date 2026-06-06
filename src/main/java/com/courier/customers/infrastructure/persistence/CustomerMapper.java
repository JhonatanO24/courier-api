package com.courier.customers.infrastructure.persistence;

import com.courier.customers.domain.models.Customer;
import com.courier.customers.domain.models.CustomerRole;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer toDomain(CustomerJpaEntity entity) {
        return new Customer(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getPassword(),
                CustomerRole.valueOf(entity.getRole().name()),
                entity.isActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public CustomerJpaEntity toEntity(Customer domain) {
        return CustomerJpaEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .email(domain.getEmail())
                .password(domain.getPassword())
                .role(CustomerJpaEntity.RoleEnum.valueOf(domain.getRole().name()))
                .isActive(domain.isActive())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
