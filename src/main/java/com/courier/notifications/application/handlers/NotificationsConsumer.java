package com.courier.notifications.application.handlers;

import com.courier.shared.events.domain.ShipmentEvent;
import com.courier.shared.events.domain.ShipmentTopics;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationsConsumer {

    private static final Logger log = LoggerFactory.getLogger(NotificationsConsumer.class);

    @KafkaListener(
            topics = {
                    ShipmentTopics.DISPATCHED,
                    ShipmentTopics.IN_CUSTOMS,
                    ShipmentTopics.FAILED
            },
            groupId = "notifications-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handle(ConsumerRecord<String, ShipmentEvent> record) {
        ShipmentEvent event = record.value();
        log.info("""
            [NotificationsConsumer] *** NOTIFICATION SENT ***
              topic        : {}
              shipmentId   : {}
              senderId     : {}
              recipientId  : {}
              type         : {}
              status       : {}
              shippingCost : {}
              timestamp    : {}
            """,
                record.topic(),
                event.getShipmentId(),
                event.getSenderId(),
                event.getRecipientId(),
                event.getType(),
                event.getStatus(),
                event.getShippingCost(),
                event.getTimestamp()
        );
    }
}
