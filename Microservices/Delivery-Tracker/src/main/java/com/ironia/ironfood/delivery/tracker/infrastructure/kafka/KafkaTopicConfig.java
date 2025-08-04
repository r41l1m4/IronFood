package com.ironia.ironfood.delivery.tracker.infrastructure.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.topics.delivery.name}")
    private String deliveriesTopicName;

    @Bean
    public NewTopic deliveryEventsTopic() {
        return TopicBuilder.name(deliveriesTopicName)
                .partitions(3)
                .replicas(1)
                .build();
    }
}