package com.ironia.ironiafood.delivery.tracker.domain.service;

import com.ironia.ironiafood.delivery.tracker.domain.exception.DomainException;
import com.ironia.ironiafood.delivery.tracker.domain.model.Delivery;
import com.ironia.ironiafood.delivery.tracker.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryCheckpointService {

    private final DeliveryRepository deliveryRepository;

    public void place(UUID deliveryId) {
        Delivery delivery = getDeliveryById(deliveryId);

        delivery.place();
        deliveryRepository.saveAndFlush(delivery);
    }

    public void pickup(UUID deliveryId, UUID courierId) {
        Delivery delivery = getDeliveryById(deliveryId);

        delivery.pickUp(courierId);
        deliveryRepository.saveAndFlush(delivery);
    }

    public void complete(UUID deliveryId) {
        Delivery delivery = getDeliveryById(deliveryId);

        delivery.markAsDelivered();
        deliveryRepository.saveAndFlush(delivery);
    }

    private Delivery getDeliveryById(UUID deliveryId) {
        return deliveryRepository.findById(deliveryId)
                .orElseThrow(DomainException::new);
    }
}
