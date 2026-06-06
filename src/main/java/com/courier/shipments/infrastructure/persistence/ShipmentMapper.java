package com.courier.shipments.infrastructure.persistence;

import com.courier.shipments.domain.models.Shipment;
import org.springframework.stereotype.Component;

@Component
public class ShipmentMapper {

    public Shipment toDomain(ShipmentJpaEntity entity) {
        return Shipment.builder()
                .id(entity.getId())
                .senderId(entity.getSenderId())
                .recipientId(entity.getRecipientId())
                .declaredValue(entity.getDeclaredValue())
                .shippingCost(entity.getShippingCost())
                .type(entity.getType())
                .status(entity.getStatus())
                .metadata(entity.getMetadata())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public ShipmentJpaEntity toEntity(Shipment domain) {
        return ShipmentJpaEntity.builder()
                .id(domain.getId())
                .senderId(domain.getSenderId())
                .recipientId(domain.getRecipientId())
                .declaredValue(domain.getDeclaredValue())
                .shippingCost(domain.getShippingCost())
                .type(domain.getType())
                .status(domain.getStatus())
                .metadata(domain.getMetadata())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
