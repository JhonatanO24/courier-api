package com.courier.shipments.application.usecases;

import com.courier.customers.domain.exceptions.CustomerNotFoundException;
import com.courier.customers.domain.ports.CustomerRepository;
import com.courier.shared.events.domain.ShipmentEvent;
import com.courier.shared.events.domain.ShipmentTopics;
import com.courier.shared.events.domain.ports.EventPublisher;
import com.courier.shipments.domain.exceptions.InvalidShipmentException;
import com.courier.shipments.domain.models.Shipment;
import com.courier.shipments.domain.models.ShipmentStatus;
import com.courier.shipments.domain.models.ShipmentType;
import com.courier.shipments.domain.ports.ShipmentRepository;
import com.courier.shipments.domain.ports.ShippingStrategyPort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CreateShipmentUseCase {

    private final ShipmentRepository shipmentRepository;
    private final CustomerRepository customerRepository;
    private final EventPublisher eventPublisher;
    private final Map<ShipmentType, ShippingStrategyPort> strategyMap;

    public CreateShipmentUseCase(
            ShipmentRepository shipmentRepository,
            CustomerRepository customerRepository,
            EventPublisher eventPublisher,
            List<ShippingStrategyPort> strategies
    ) {
        this.shipmentRepository = shipmentRepository;
        this.customerRepository = customerRepository;
        this.eventPublisher = eventPublisher;
        this.strategyMap = strategies.stream()
                .collect(Collectors.toMap(ShippingStrategyPort::getSupportedType, s -> s));
    }

    public Shipment execute(Shipment shipment) {
        // 1. Validar existencia de clientes
        var sender = customerRepository.findById(shipment.getSenderId())
                .orElseThrow(() -> new CustomerNotFoundException(shipment.getSenderId()));
        var recipient = customerRepository.findById(shipment.getRecipientId())
                .orElseThrow(() -> new CustomerNotFoundException(shipment.getRecipientId()));

        // 2. Reglas comunes
        if (!sender.isActive() || !recipient.isActive())
            throw new InvalidShipmentException("Both customers must be active");

        if (sender.getId().equals(recipient.getId()))
            throw new InvalidShipmentException("Sender and recipient cannot be the same");

        if (shipment.getDeclaredValue() == null || shipment.getDeclaredValue().compareTo(BigDecimal.valueOf(0)) <= 0)
            throw new InvalidShipmentException("Declared value must be greater than 0");

        // 3. Selección de estrategia
        ShippingStrategyPort strategy = strategyMap.get(shipment.getType());
        if (strategy == null)
            throw new InvalidShipmentException("Unknown shipment type: " + shipment.getType());

        // 4. Preparación del objeto de dominio
        // IMPORTANTE: Forzamos el ID a null para que JPA haga un INSERT (nuevo) y no un MERGE (actualización)
        shipment.setId(null);

        strategy.validate(shipment);
        shipment.setShippingCost(strategy.calculateCost(shipment));
        shipment.setStatus(strategy.getFinalStatus());
        shipment.setCreatedAt(LocalDateTime.now());
        shipment.setUpdatedAt(LocalDateTime.now());

        // 5. Persistir
        // Ahora sí, JPA verá el ID nulo y generará uno nuevo sin quejarse de bloqueos optimistas
        Shipment saved = shipmentRepository.save(shipment);

        // 6. Publicar evento (patrón Observer)
        publishShipmentEvent(saved);

        return saved;
    }

    private void publishShipmentEvent(Shipment saved) {
        ShipmentEvent event = new ShipmentEvent(
                saved.getId(),
                saved.getSenderId(),
                saved.getRecipientId(),
                saved.getDeclaredValue(),
                saved.getShippingCost(),
                saved.getType().name(),
                saved.getStatus().name()
        );

        String topic = resolveTopic(saved.getStatus());
        eventPublisher.publish(topic, event);
    }

    private String resolveTopic(ShipmentStatus status) {
        return switch (status) {
            case DELIVERED   -> ShipmentTopics.DISPATCHED;
            case IN_CUSTOMS  -> ShipmentTopics.IN_CUSTOMS;
            case FAILED      -> ShipmentTopics.FAILED;
            default          -> ShipmentTopics.FAILED;
        };
    }
}