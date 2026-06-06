package com.courier.shipments.domain.exceptions;

public class InvalidShipmentException extends RuntimeException {
    public InvalidShipmentException(String message) {
        super(message);
    }
}
