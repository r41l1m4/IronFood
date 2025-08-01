package com.ironia.ironiafood.delivery.tracker.domain.model;

import java.util.Arrays;
import java.util.List;

public enum DeliveryStatus {
    DRAFT,
    WAITING_FOR_COURIER(DRAFT),
    IN_TRANSIT(WAITING_FOR_COURIER),
    DELIVERED(IN_TRANSIT);

    private final List<DeliveryStatus> previousStatuses;

    DeliveryStatus(DeliveryStatus... previousStatuses) {
        this.previousStatuses = Arrays.asList(previousStatuses);
    }

    public boolean canChangeTo(DeliveryStatus newStatus) {
        DeliveryStatus current = this;
        return newStatus.previousStatuses.contains(current);
    }

    public boolean canNotChangeTo(DeliveryStatus newStatus) {
        return !canChangeTo(newStatus);
    }
}
