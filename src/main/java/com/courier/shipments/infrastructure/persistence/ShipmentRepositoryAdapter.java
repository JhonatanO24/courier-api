package com.courier.shipments.infrastructure.persistence;

import com.courier.shipments.domain.models.Shipment;
import com.courier.shipments.domain.ports.ShipmentRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ShipmentRepositoryAdapter implements ShipmentRepository {

    private final ShipmentJpaRepository jpaRepository;
    private final ShipmentMapper mapper;

    public ShipmentRepositoryAdapter(ShipmentJpaRepository jpaRepository, ShipmentMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Shipment save(Shipment shipment) {
        ShipmentJpaEntity entity = mapper.toEntity(shipment);
        ShipmentJpaEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Shipment> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Shipment> findByCustomerId(UUID customerId) {
        return jpaRepository.findBySenderIdOrRecipientId(customerId, customerId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
