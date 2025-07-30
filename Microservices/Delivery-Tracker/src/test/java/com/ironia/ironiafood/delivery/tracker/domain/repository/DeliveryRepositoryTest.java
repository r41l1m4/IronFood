package com.ironia.ironiafood.delivery.tracker.domain.repository;

import com.ironia.ironiafood.delivery.tracker.domain.model.ContactPoint;
import com.ironia.ironiafood.delivery.tracker.domain.model.Delivery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DeliveryRepositoryTest {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Test
    public void shouldPersist() {
        Delivery delivery = Delivery.draft();

        delivery.editPreflightDetails(createdValidPreflightDetails());

        delivery.addItem("Computer", 2);
        delivery.addItem("Notebook", 2);

        deliveryRepository.saveAndFlush(delivery);

        Delivery persistedDelivery = deliveryRepository.findById(delivery.getId()).orElseThrow();

        assertEquals(2, persistedDelivery.getItems().size());
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