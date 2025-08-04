package com.ironia.ironiafood.delivery.tracker.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@ToString
public class DeliveryPlacedEvent {

    private final OffsetDateTime occuredAt;
    private final UUID deliveryId;
}
