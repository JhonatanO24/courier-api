package com.courier.shipments.application.strategies;

import com.courier.shipments.domain.exceptions.InvalidShipmentException;
import com.courier.shipments.domain.models.Shipment;
import com.courier.shipments.domain.models.ShipmentStatus;
import com.courier.shipments.domain.models.ShipmentType;
import com.courier.shipments.domain.ports.ShippingStrategyPort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ExpressShippingStrategy implements ShippingStrategyPort {

    @Override
    public void validate(Shipment shipment) {

        double weight = Double.parseDouble(shipment.getMetadata().getOrDefault("weightKg", 0).toString());
        if (weight > 5) throw new InvalidShipmentException("EXPRESS weight cannot exceed 5kg");

        BigDecimal limit = new BigDecimal("3000000");
        if (shipment.getDeclaredValue().compareTo(limit) > 0) {
            throw new InvalidShipmentException("EXPRESS value cannot exceed 3,000,000");
        }
    }

    @Override
    public BigDecimal calculateCost(Shipment shipment) {
        return new BigDecimal("15000");
    }

    @Override
    public ShipmentStatus getFinalStatus() { return ShipmentStatus.DELIVERED; }

    @Override
    public ShipmentType getSupportedType() { return ShipmentType.EXPRESS; }
}
