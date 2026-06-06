package com.courier.shipments.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Shipment {
    private UUID id;
    private UUID senderId;
    private UUID recipientId;
    private BigDecimal declaredValue;
    private BigDecimal shippingCost;
    private ShipmentType type;
    private ShipmentStatus status;
    private Map<String, Object> metadata; // Aquí guardamos peso, país, etc.
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
