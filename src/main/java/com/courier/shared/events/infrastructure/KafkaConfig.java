package com.courier.shared.events.infrastructure;

import com.courier.shared.events.domain.ShipmentEvent;
import com.courier.shared.events.domain.ShipmentTopics;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    // Crea los topics automáticamente al arrancar
    @Bean
    public NewTopic topicDispatched() {
        return TopicBuilder.name(ShipmentTopics.DISPATCHED).partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic topicInCustoms() {
        return TopicBuilder.name(ShipmentTopics.IN_CUSTOMS).partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic topicFailed() {
        return TopicBuilder.name(ShipmentTopics.FAILED).partitions(1).replicas(1).build();
    }

    // Consumer factory compartida por ambos consumers
    @Bean
    public ConsumerFactory<String, ShipmentEvent> consumerFactory() {
        JsonDeserializer<ShipmentEvent> deserializer = new JsonDeserializer<>(ShipmentEvent.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("com.courier.*");
        deserializer.setUseTypeMapperForKey(false);

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ShipmentEvent> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ShipmentEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}