package com.ironia.ironiafood.courier.manager.domain.model;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Courier {

    @EqualsAndHashCode.Include
    private UUID id;

    @Setter(AccessLevel.PUBLIC)
    private String name;

    @Setter(AccessLevel.PUBLIC)
    private String phone;

    private Integer fulfilledDeliveriesQuantity;
    private Integer pendingDeliveriesQuantity;
    private OffsetDateTime lastFulfilledDeliveryAt;
    private List<AssignedDelivery> pendingDeliveries = new ArrayList<>();

    public List<AssignedDelivery> getPendingDeliveries() {
        return Collections.unmodifiableList(this.pendingDeliveries);
    }

    public static Courier brandNew(String name, String phone) {
        Courier courier = new Courier();

        courier.setId(UUID.randomUUID());
        courier.setName(name);
        courier.setPhone(phone);
        courier.setPendingDeliveriesQuantity(0);
        courier.setFulfilledDeliveriesQuantity(0);

        return courier;
    }

    public void assign(UUID deliveryId) {
        this.pendingDeliveries.add(
                AssignedDelivery.pending(deliveryId)
        );

        updatePendingDeliveriesQuantity(1);
    }

    public void fulfill(UUID deliveryId) {
        AssignedDelivery currentDelivery = this.pendingDeliveries.stream()
                .filter(delivery -> delivery.getId().equals(deliveryId))
                .findFirst().orElseThrow();

        this.pendingDeliveries.remove(currentDelivery);

        updatePendingDeliveriesQuantity(-1);
        updateFulfilledDeliveriesQuantity(1);
        this.lastFulfilledDeliveryAt = OffsetDateTime.now();
    }

    private void updatePendingDeliveriesQuantity(int quantity) {
        this.setPendingDeliveriesQuantity(
                this.getPendingDeliveriesQuantity() + quantity
        );
    }

    private void updateFulfilledDeliveriesQuantity(int quantity) {
        this.setFulfilledDeliveriesQuantity(
                this.getFulfilledDeliveriesQuantity() + quantity
        );
    }
}
