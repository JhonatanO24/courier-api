package com.courier.shared.events.domain;

public final class ShipmentTopics {

    private ShipmentTopics() {
    }

    public static final String DISPATCHED = "shipment.dispatched";
    public static final String IN_CUSTOMS = "shipment.in_customs";
    public static final String FAILED = "shipment.failed";
}
