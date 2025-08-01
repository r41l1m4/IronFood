package com.ironia.ironiafood.delivery.tracker.domain.model;

import com.ironia.ironiafood.delivery.tracker.domain.exception.DomainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryTest {

    private Delivery deliveryDraft;
    private final UUID courierId = UUID.randomUUID();

    @BeforeEach
    void draftWith2ItemsSetUp() {
        this.deliveryDraft = Delivery.draft();
        this.deliveryDraft.editPreflightDetails(createdValidPreflightDetails());

        this.deliveryDraft.addItem("Patch cord", 1);
        this.deliveryDraft.addItem("RJ-45 extensor", 1);
    }

    @Test
    public void shouldChangeToPlaced() {
        this.deliveryDraft.place();

        assertEquals(DeliveryStatus.WAITING_FOR_COURIER, this.deliveryDraft.getStatus());
        assertNotNull(deliveryDraft.getPlacedAt());
    }

    @Test
    public void shouldNotPlace() {
        Delivery deliveryDraft = Delivery.draft();

        assertThrows(DomainException.class, deliveryDraft::place);

        assertEquals(DeliveryStatus.DRAFT, deliveryDraft.getStatus());
        assertNull(deliveryDraft.getPlacedAt());
    }

    @Test
    void shouldHaveTwoItems() {
        assertEquals(2, deliveryDraft.getTotalItems());
    }

    @Test
    void shouldRemoveOneItem() {
        UUID nicId = this.deliveryDraft.addItem("Gigabit NIC", 1);

        assertEquals(3, deliveryDraft.getTotalItems());

        this.deliveryDraft.removeItem(nicId);

        assertEquals(2, deliveryDraft.getTotalItems());
    }

    @Test
    void shouldRemoveAllItems() {
        this.deliveryDraft.removeItems();

        assertEquals(0, deliveryDraft.getTotalItems());
    }

    @Test
    void shouldchangeItemQuantity() {
        UUID nicId = this.deliveryDraft.addItem("Gigabit NIC", 1);
        Integer nicQuantity = this.deliveryDraft.getItems().stream()
                        .filter(item -> item.getId().equals(nicId))
                        .mapToInt(Item::getQuantity).sum();

        assertEquals(1, nicQuantity);

        this.deliveryDraft.changeItemQuantity(nicId, 3);
        nicQuantity = this.deliveryDraft.getItems().stream()
                .filter(item -> item.getId().equals(nicId))
                .mapToInt(Item::getQuantity).sum();

        assertEquals(3, nicQuantity);
    }

    @Test
    void shouldChangeStatusToInTransitWhenSetToPickUp() {
        this.deliveryDraft.place();

        assertEquals(DeliveryStatus.WAITING_FOR_COURIER, this.deliveryDraft.getStatus());

        this.deliveryDraft.pickUp(this.courierId);

        assertEquals(DeliveryStatus.IN_TRANSIT, this.deliveryDraft.getStatus());
    }

    @Test
    void shouldChangeStatusToDeliveredWhenMarkedAsDelivered() {
        this.deliveryDraft.place();
        this.deliveryDraft.pickUp(this.courierId);

        assertEquals(DeliveryStatus.IN_TRANSIT, this.deliveryDraft.getStatus());

        this.deliveryDraft.markAsDelivered();

        assertEquals(DeliveryStatus.DELIVERED, this.deliveryDraft.getStatus());
    }

    @Test
    void shouldThrowExceptionWhenEditPreflighDetailsOfNonDraftDelivery() {
        this.deliveryDraft.place();

        assertThrows(DomainException.class,
                () -> this.deliveryDraft.editPreflightDetails(createdValidPreflightDetails()));
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