package com.courier.shared.events.domain.ports;

import com.courier.shared.events.domain.ShipmentEvent;

public interface EventPublisher {
    void publish(String topic, ShipmentEvent event);
}
