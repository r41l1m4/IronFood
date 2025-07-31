package com.ironia.ironiafood.delivery.tracker.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryStatusTest {

    @Test
    void draft_canChangeToWaitingForCourier() {
        assertTrue(
                DeliveryStatus.DRAFT.canChangeTo(DeliveryStatus.WAITING_FOR_COURIER)
        );
    }
    @Test
    void waitingForCourier_canChangeInTransit() {
        assertTrue(
                DeliveryStatus.WAITING_FOR_COURIER.canChangeTo(DeliveryStatus.IN_TRANSIT)
        );
    }

     @Test
    void inTransit_canChangeDelivered() {
        assertTrue(
                DeliveryStatus.IN_TRANSIT.canChangeTo(DeliveryStatus.DELIVERED)
        );
    }

    @Test
    void draft_canChangeToInTransit() {
        assertTrue(
                DeliveryStatus.DRAFT.canNotChangeTo(DeliveryStatus.IN_TRANSIT)
        );
    }

    @Test
    void intTransit_canNotChangeToWaitingForCourier() {
        assertTrue(
                DeliveryStatus.IN_TRANSIT.canNotChangeTo(DeliveryStatus.WAITING_FOR_COURIER)
        );
    }
}