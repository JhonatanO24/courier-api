package com.courier.shipments.infrastructure.dtos;

import com.courier.shipments.domain.models.Shipment;
import com.courier.shipments.domain.models.ShipmentStatus;
import com.courier.shipments.domain.models.ShipmentType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
public class ShipmentResponse {

    private UUID id;
    private UUID senderId;
    private UUID recipientId;
    private BigDecimal declaredValue;
    private BigDecimal shippingCost;
    private ShipmentType type;
    private ShipmentStatus status;
    private Map<String, Object> metadata;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ShipmentResponse from(Shipment shipment) {
        ShipmentResponse response = new ShipmentResponse();
        response.setId(shipment.getId());
        response.setSenderId(shipment.getSenderId());
        response.setRecipientId(shipment.getRecipientId());
        response.setDeclaredValue(shipment.getDeclaredValue());
        response.setShippingCost(shipment.getShippingCost());
        response.setType(shipment.getType());
        response.setStatus(shipment.getStatus());
        response.setMetadata(shipment.getMetadata());
        response.setCreatedAt(shipment.getCreatedAt());
        response.setUpdatedAt(shipment.getUpdatedAt());
        return response;
    }
}
