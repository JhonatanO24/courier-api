package com.courier.shared.events.infrastructure;

import com.courier.shared.events.domain.ShipmentEvent;
import com.courier.shared.events.domain.ports.EventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaEventPublisher implements EventPublisher {

    private static final Logger log = LoggerFactory.getLogger(KafkaEventPublisher.class);
    private final KafkaTemplate<String, ShipmentEvent> kafkaTemplate;

    public KafkaEventPublisher(KafkaTemplate<String, ShipmentEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(String topic, ShipmentEvent event) {
        kafkaTemplate.send(topic, event.getShipmentId().toString(), event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("[KafkaPublisher] Failed to publish event to topic={} shipmentId={}",
                                topic, event.getShipmentId(), ex);
                    } else {
                        log.info("[KafkaPublisher] Event published topic={} shipmentId={} offset={}",
                                topic, event.getShipmentId(),
                                result.getRecordMetadata().offset());
                    }
                });
    }
}
