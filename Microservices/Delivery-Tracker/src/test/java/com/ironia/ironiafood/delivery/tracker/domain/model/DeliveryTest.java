package com.ironia.ironiafood.delivery.tracker.domain.model;

import com.ironia.ironiafood.delivery.tracker.domain.exception.DomainException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryTest {

    @Test
    public void shouldChangeToPlaced() {
        Delivery deliveryDraft = Delivery.draft();

        deliveryDraft.editPreflightDetails(createdValidPreflightDetails());
        deliveryDraft.place();

        assertEquals(DeliveryStatus.WAITING_FOR_COURIER, deliveryDraft.getStatus());
        assertNotNull(deliveryDraft.getPlacedAt());
    }

    @Test
    public void shouldNotPlace() {
        Delivery deliveryDraft = Delivery.draft();

        assertThrows(DomainException.class, deliveryDraft::place);

        assertEquals(DeliveryStatus.DRAFT, deliveryDraft.getStatus());
        assertNull(deliveryDraft.getPlacedAt());
    }

    private Delivery.PreflightDetails createdValidPreflightDetails() {

        ContactPoint sender = ContactPoint.builder()
                .zipCode("57327-000")
                .street("Rua dos Sem Nada")
                .number("100")
                .complement("Sala 101")
                .name("Inocêncio do Santos")
                .phone("(91) 93476-5249")
                .build();

        ContactPoint recipient = ContactPoint.builder()
                .zipCode("57327-000")
                .street("Rua Lavada")
                .number("2054")
                .complement("Big Mercado")
                .name("Ariel das Águas")
                .phone("(91) 97676-7249")
                .build();


        return Delivery.PreflightDetails.builder()
                .sender(sender)
                .recipient(recipient)
                .distanceFee(new BigDecimal("15.00"))
                .courierPayout(new BigDecimal("5.00"))
                .expectedDeliveryTime((Duration.ofHours(5)))
                .build();
    }
}