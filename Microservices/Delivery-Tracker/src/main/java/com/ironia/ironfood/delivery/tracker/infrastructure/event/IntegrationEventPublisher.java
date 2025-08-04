package com.ironia.ironfood.delivery.tracker.infrastructure.event;

public interface IntegrationEventPublisher {

    void publish(Object event, String key, String topic);
}
