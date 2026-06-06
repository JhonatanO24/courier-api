package com.courier.shipments.application.strategies;

import com.courier.shipments.domain.exceptions.InvalidShipmentException;
import com.courier.shipments.domain.models.Shipment;
import com.courier.shipments.domain.models.ShipmentStatus;
import com.courier.shipments.domain.models.ShipmentType;
import com.courier.shipments.domain.ports.ShippingStrategyPort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class StandardShippingStrategy implements ShippingStrategyPort {

    @Override
    public void validate(Shipment shipment) {
        double weight = Double.parseDouble(shipment.getMetadata().getOrDefault("weightKg", 0).toString());
        if (weight > 20) throw new InvalidShipmentException("STANDARD weight cannot exceed 20kg");
    }

    @Override
    public BigDecimal calculateCost(Shipment shipment) {
        // 0.1% del valor declarado
        BigDecimal percentage = new BigDecimal("0.001");
        BigDecimal calculatedCost = shipment.getDeclaredValue().multiply(percentage);

        // Mínimo de 5000
        BigDecimal minCost = new BigDecimal("5000");
        return calculatedCost.max(minCost);
    }

    @Override
    public ShipmentStatus getFinalStatus() { return ShipmentStatus.DELIVERED; }

    @Override
    public ShipmentType getSupportedType() { return ShipmentType.STANDARD; }
}
