package com.courier.shipments.application.usecases;

import com.courier.customers.domain.exceptions.CustomerNotFoundException;
import com.courier.customers.domain.ports.CustomerRepository;
import com.courier.shipments.domain.models.Shipment;
import com.courier.shipments.domain.ports.ShipmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FindShipmentsByCustomerUseCase {

    private final ShipmentRepository shipmentRepository;
    private final CustomerRepository customerRepository;

    public FindShipmentsByCustomerUseCase(
            ShipmentRepository shipmentRepository,
            CustomerRepository customerRepository
    ) {
        this.shipmentRepository = shipmentRepository;
        this.customerRepository = customerRepository;
    }

    public List<Shipment> execute(UUID customerId) {
        customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        return shipmentRepository.findByCustomerId(customerId);
    }
}
