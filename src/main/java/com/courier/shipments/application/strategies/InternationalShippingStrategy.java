package com.courier.shipments.application.strategies;

import com.courier.shipments.domain.models.Shipment;
import com.courier.shipments.domain.models.ShipmentStatus;
import com.courier.shipments.domain.models.ShipmentType;
import com.courier.shipments.domain.ports.ShippingStrategyPort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class InternationalShippingStrategy implements ShippingStrategyPort {

    @Override
    public void validate(Shipment shipment) {
        if (!shipment.getMetadata().containsKey("destinationCountry") ||
                !shipment.getMetadata().containsKey("customsDeclaration")) {
            throw new RuntimeException("International shipping requires country and customs declaration");
        }

        BigDecimal limit = new BigDecimal("50000000");
        if (shipment.getDeclaredValue().compareTo(limit) > 0) {
            throw new RuntimeException("INTERNATIONAL value exceeds 50M limit");
        }
    }

    @Override
    public BigDecimal calculateCost(Shipment shipment) {

        BigDecimal baseCost = new BigDecimal("50000");
        BigDecimal percentage = new BigDecimal("0.02");

        // Operación: base + (valor * 0.02)
        return baseCost.add(shipment.getDeclaredValue().multiply(percentage));
    }

    @Override
    public ShipmentStatus getFinalStatus() { return ShipmentStatus.IN_CUSTOMS; }

    @Override
    public ShipmentType getSupportedType() { return ShipmentType.INTERNATIONAL; }

}
