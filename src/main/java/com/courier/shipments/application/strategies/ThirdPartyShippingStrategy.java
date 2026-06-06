package com.courier.shipments.application.strategies;

import com.courier.shipments.domain.models.Shipment;
import com.courier.shipments.domain.models.ShipmentStatus;
import com.courier.shipments.domain.models.ShipmentType;
import com.courier.shipments.domain.ports.ShippingStrategyPort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ThirdPartyShippingStrategy implements ShippingStrategyPort {

    @Override
    public void validate(Shipment shipment) {
        if (!shipment.getMetadata().containsKey("carrierName") ||
                !shipment.getMetadata().containsKey("externalTrackingId")) {
            throw new RuntimeException("Third party shipping requires carrier name and external tracking ID");
        }
    }

    @Override
    public BigDecimal calculateCost(Shipment shipment) {
        // 5% del valor declarado
        BigDecimal percentage = new BigDecimal("0.05");
        return shipment.getDeclaredValue().multiply(percentage);
    }

    @Override
    public ShipmentStatus getFinalStatus() { return ShipmentStatus.DELIVERED; }

    @Override
    public ShipmentType getSupportedType() { return ShipmentType.THIRD_PARTY_CARRIER; }
}
