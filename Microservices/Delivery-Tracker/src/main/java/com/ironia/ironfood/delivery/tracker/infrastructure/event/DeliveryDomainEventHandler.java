package com.ironia.ironfood.delivery.tracker.infrastructure.event;

import com.ironia.ironfood.delivery.tracker.domain.event.DeliveryFulfilledEvent;
import com.ironia.ironfood.delivery.tracker.domain.event.DeliveryPickedUpEvent;
import com.ironia.ironfood.delivery.tracker.domain.event.DeliveryPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class DeliveryDomainEventHandler {

    private final IntegrationEventPublisher integrationEventPublisher;

    @Value("${kafka.topics.delivery.name}")
    private String deliveriesTopicName;

    @EventListener
    public void handle(DeliveryPlacedEvent event) {
        log.info(event.toString());
        integrationEventPublisher.publish(event, event.getDeliveryId().toString(), deliveriesTopicName);
    }

    @EventListener
    public void handle(DeliveryPickedUpEvent event) {
        log.info(event.toString());
        integrationEventPublisher.publish(event, event.getDeliveryId().toString(), deliveriesTopicName);
    }

    @EventListener
    public void handle(DeliveryFulfilledEvent event) {
        log.info(event.toString());
        integrationEventPublisher.publish(event, event.getDeliveryId().toString(), deliveriesTopicName);
    }
}
