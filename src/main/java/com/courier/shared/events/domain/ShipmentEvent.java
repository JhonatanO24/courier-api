package com.courier.shared.events.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class ShipmentEvent {

    private UUID shipmentId;
    private UUID senderId;
    private UUID recipientId;
    private BigDecimal declaredValue;
    private BigDecimal shippingCost;
    private String type;
    private String status;
    private LocalDateTime timestamp;

    public ShipmentEvent() {}

    // Constructor actualizado para recibir BigDecimal
    public ShipmentEvent(UUID shipmentId, UUID senderId, UUID recipientId,
                         BigDecimal declaredValue, BigDecimal shippingCost,
                         String type, String status) {
        this.shipmentId = shipmentId;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.declaredValue = declaredValue;
        this.shippingCost = shippingCost;
        this.type = type;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    // Getters y Setters
    public UUID getShipmentId() { return shipmentId; }
    public void setShipmentId(UUID shipmentId) { this.shipmentId = shipmentId; }

    public UUID getSenderId() { return senderId; }
    public void setSenderId(UUID senderId) { this.senderId = senderId; }

    public UUID getRecipientId() { return recipientId; }
    public void setRecipientId(UUID recipientId) { this.recipientId = recipientId; }

    public BigDecimal getDeclaredValue() { return declaredValue; }
    public void setDeclaredValue(BigDecimal declaredValue) { this.declaredValue = declaredValue; }

    public BigDecimal getShippingCost() { return shippingCost; }
    public void setShippingCost(BigDecimal shippingCost) { this.shippingCost = shippingCost; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
