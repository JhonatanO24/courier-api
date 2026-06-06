package com.courier.notifications.application.handlers;

import com.courier.shared.events.domain.ShipmentEvent;
import com.courier.shared.events.domain.ShipmentTopics;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AuditConsumer {

    private static final Logger log = LoggerFactory.getLogger(AuditConsumer.class);

    @KafkaListener(
            topics = {
                    ShipmentTopics.DISPATCHED,
                    ShipmentTopics.IN_CUSTOMS,
                    ShipmentTopics.FAILED
            },
            groupId = "audit-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handle(ConsumerRecord<String, ShipmentEvent> record) {
        ShipmentEvent event = record.value();
        log.info("""
            [AuditConsumer] *** AUDIT TRACE ***
              topic        : {}
              partition    : {}
              offset       : {}
              shipmentId   : {}
              status       : {}
              declaredValue: {}
              shippingCost : {}
              timestamp    : {}
            """,
                record.topic(),
                record.partition(),
                record.offset(),
                event.getShipmentId(),
                event.getStatus(),
                event.getDeclaredValue(),
                event.getShippingCost(),
                event.getTimestamp()
        );
    }
}
