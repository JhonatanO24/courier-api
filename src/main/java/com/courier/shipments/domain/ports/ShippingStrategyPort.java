package com.courier.shipments.domain.ports;

import com.courier.shipments.domain.models.Shipment;
import com.courier.shipments.domain.models.ShipmentStatus;
import com.courier.shipments.domain.models.ShipmentType;

import java.math.BigDecimal;

public interface ShippingStrategyPort {
    void validate(Shipment shipment);
    BigDecimal calculateCost(Shipment shipment);
    ShipmentStatus getFinalStatus();
    ShipmentType getSupportedType();
}
