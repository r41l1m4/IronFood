package com.ironia.ironiafood.courier.manager.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CourierTest {

    private final String courierName = "IroniaLog";
    private final String courierPhone = "(93) 96473-6068";
    private Courier newCourier;

    @BeforeEach
    void newCourierSetUp() {
        this.newCourier = Courier.brandNew(courierName, courierPhone);
    }

    @Test
    void shouldCreateValidNewCourier() {
        Courier courier = Courier.brandNew(this.courierName, this.courierPhone);

        assertNotNull(courier.getId());
        assertEquals(this.courierName, courier.getName());
        assertEquals(this.courierPhone, courier.getPhone());
        assertEquals(0, courier.getPendingDeliveriesQuantity());
        assertEquals(0, courier.getFulfilledDeliveriesQuantity());

    }

    @Test
    void itStartsWithZeroPendingDeliveries() {
        assertEquals(0, this.newCourier.getPendingDeliveries().size());
    }

    @Test
    void shouldAssignTwoPendingDeliveries() {
        UUID deliveryId1 = UUID.randomUUID();
        UUID deliveryId2 = UUID.randomUUID();

        this.newCourier.assign(deliveryId1);
        this.newCourier.assign(deliveryId2);

        assertEquals(2, this.newCourier.getPendingDeliveries().size());
    }

    @Test
    void shouldFulfillOneOfTwoDeliveries() {
        UUID deliveryId1 = UUID.randomUUID();
        UUID deliveryId2 = UUID.randomUUID();

        this.newCourier.assign(deliveryId1);
        this.newCourier.assign(deliveryId2);

        this.newCourier.fulfill(deliveryId2);

        assertEquals(1, this.newCourier.getPendingDeliveries().size());
    }

}