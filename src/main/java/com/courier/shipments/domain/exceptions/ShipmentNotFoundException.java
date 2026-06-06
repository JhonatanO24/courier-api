package com.courier.shipments.domain.exceptions;

import java.util.UUID;

public class ShipmentNotFoundException extends RuntimeException {
    public ShipmentNotFoundException(UUID id) {
        super("Shipment not found with id: " + id);
    }
}
