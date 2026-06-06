package com.courier.shipments.domain.ports;

import com.courier.shipments.domain.models.Shipment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ShipmentRepository {
    Shipment save(Shipment shipment);
    Optional<Shipment> findById(UUID id);
    List<Shipment> findByCustomerId(UUID customerId);
}
