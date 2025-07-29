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
    void draft_canChangeToInTransit() {
        assertTrue(
                DeliveryStatus.DRAFT.cannotChangeTo(DeliveryStatus.IN_TRANSIT)
        );
    }

}