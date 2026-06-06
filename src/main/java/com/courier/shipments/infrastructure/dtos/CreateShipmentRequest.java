package com.courier.shipments.infrastructure.dtos;

import com.courier.shipments.domain.models.ShipmentType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Data
public class CreateShipmentRequest {

    @NotNull(message = "Sender ID is required")
    private UUID senderId;

    @NotNull(message = "Recipient ID is required")
    private UUID recipientId;

    @NotNull(message = "Declared value is required")
    @Positive(message = "Declared value must be greater than 0")
    private BigDecimal declaredValue;

    @NotNull(message = "Shipment type is required")
    private ShipmentType type;

    @NotNull(message = "Metadata is required")
    private Map<String, Object> metadata;
}
