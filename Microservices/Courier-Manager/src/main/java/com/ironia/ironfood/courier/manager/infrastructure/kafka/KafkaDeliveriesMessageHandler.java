package com.ironia.ironfood.courier.manager.infrastructure.kafka;

import com.ironia.ironfood.courier.manager.domain.service.CourierDeliveryService;
import com.ironia.ironfood.courier.manager.infrastructure.event.DeliveryFulfilledIntegrationEvent;
import com.ironia.ironfood.courier.manager.infrastructure.event.DeliveryPlacedIntegrationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = {"${kafka.topics.delivery.name}"}, groupId = "${spring.application.name}")
@Slf4j
@RequiredArgsConstructor
public class KafkaDeliveriesMessageHandler {

    private final CourierDeliveryService courierDeliveryService;

    @KafkaHandler(isDefault = true)
    public void defaultHandler(@Payload Object payload) {
        log.info("Default handler: {}" , payload);
    }

    @KafkaHandler
    public void handle(@Payload DeliveryPlacedIntegrationEvent event) {
        log.info("Received: {}", event);
        courierDeliveryService.assign(event.getDeliveryId());
    }

    @KafkaHandler
    public void handle(@Payload DeliveryFulfilledIntegrationEvent event) {
        log.info("Received: {}", event);
        courierDeliveryService.fulfill((event.getDeliveryId()));
    }
}
