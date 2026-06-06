package com.courier.shipments.application.usecases;

import com.courier.shipments.domain.exceptions.ShipmentNotFoundException;
import com.courier.shipments.domain.models.Shipment;
import com.courier.shipments.domain.ports.ShipmentRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FindShipmentByIdUseCase {

    private final ShipmentRepository shipmentRepository;

    public FindShipmentByIdUseCase(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    public Shipment execute(UUID id) {
        return shipmentRepository.findById(id)
                .orElseThrow(() -> new ShipmentNotFoundException(id));
    }
}
