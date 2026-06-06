package com.courier.shipments.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ShipmentJpaRepository extends JpaRepository<ShipmentJpaEntity, UUID> {

    @Query("SELECT s FROM ShipmentJpaEntity s WHERE s.senderId = :customerId OR s.recipientId = :customerId")
    List<ShipmentJpaEntity> findBySenderIdOrRecipientId(UUID senderId, UUID recipientId);
}
