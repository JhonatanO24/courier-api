package com.courier.shipments.infrastructure.controllers;

import com.courier.shipments.application.usecases.CreateShipmentUseCase;
import com.courier.shipments.application.usecases.FindShipmentByIdUseCase;
import com.courier.shipments.application.usecases.FindShipmentsByCustomerUseCase;
import com.courier.shipments.domain.models.Shipment;
import com.courier.shipments.infrastructure.dtos.CreateShipmentRequest;
import com.courier.shipments.infrastructure.dtos.ShipmentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/shipments")
@Tag(name = "Shipments", description = "Shipment management")
public class ShipmentController {

    private final CreateShipmentUseCase createShipmentUseCase;
    private final FindShipmentByIdUseCase findShipmentByIdUseCase;
    private final FindShipmentsByCustomerUseCase findShipmentsByCustomerUseCase;

    public ShipmentController(
            CreateShipmentUseCase createShipmentUseCase,
            FindShipmentByIdUseCase findShipmentByIdUseCase,
            FindShipmentsByCustomerUseCase findShipmentsByCustomerUseCase
    ) {
        this.createShipmentUseCase = createShipmentUseCase;
        this.findShipmentByIdUseCase = findShipmentByIdUseCase;
        this.findShipmentsByCustomerUseCase = findShipmentsByCustomerUseCase;
    }

    @PostMapping
    @Operation(summary = "Create a new shipment (executes strategy + publishes event)")
    public ResponseEntity<ShipmentResponse> create(@Valid @RequestBody CreateShipmentRequest request) {
        Shipment shipment = Shipment.builder()
                .id(UUID.randomUUID())
                .senderId(request.getSenderId())
                .recipientId(request.getRecipientId())
                .declaredValue(request.getDeclaredValue())
                .type(request.getType())
                .metadata(request.getMetadata())
                .build();

        Shipment created = createShipmentUseCase.execute(shipment);
        return ResponseEntity.status(HttpStatus.CREATED).body(ShipmentResponse.from(created));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find shipment by ID")
    public ResponseEntity<ShipmentResponse> findById(@PathVariable UUID id) {
        Shipment shipment = findShipmentByIdUseCase.execute(id);
        return ResponseEntity.ok(ShipmentResponse.from(shipment));
    }

    @GetMapping("/customer/{id}")
    @Operation(summary = "Find shipments sent or received by a customer")
    public ResponseEntity<List<ShipmentResponse>> findByCustomer(@PathVariable UUID id) {
        List<ShipmentResponse> shipments = findShipmentsByCustomerUseCase.execute(id)
                .stream()
                .map(ShipmentResponse::from)
                .toList();
        return ResponseEntity.ok(shipments);
    }
}
