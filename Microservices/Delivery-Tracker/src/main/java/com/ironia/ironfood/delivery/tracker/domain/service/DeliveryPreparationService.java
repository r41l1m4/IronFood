package com.ironia.ironfood.delivery.tracker.domain.service;

import com.ironia.ironfood.delivery.tracker.api.model.ContactPointInput;
import com.ironia.ironfood.delivery.tracker.api.model.DeliveryInput;
import com.ironia.ironfood.delivery.tracker.domain.exception.DomainException;
import com.ironia.ironfood.delivery.tracker.domain.model.ContactPoint;
import com.ironia.ironfood.delivery.tracker.domain.model.Delivery;
import com.ironia.ironfood.delivery.tracker.domain.model.DeliveryEstimate;
import com.ironia.ironfood.delivery.tracker.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryPreparationService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryTimeEstimationService deliveryTimeEstimationService;
    private final CourierPayoutCalculationService courierPayoutCalculationService;

    @Transactional
    public Delivery draft(DeliveryInput input) {
        Delivery delivery = Delivery.draft();

        handlePreparation(input, delivery);

        return deliveryRepository.saveAndFlush(delivery);
    }

    @Transactional
    public Delivery edit(UUID deliveryId, DeliveryInput input) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(DomainException::new);

        delivery.removeItems();
        handlePreparation(input, delivery);

        return deliveryRepository.saveAndFlush(delivery);
    }

    private void handlePreparation(DeliveryInput input, Delivery delivery) {
        ContactPointInput senderInput = input.getSender();
        ContactPointInput recipientInput = input.getRecipient();

        ContactPoint sender = ContactPoint.builder()
                .zipCode(senderInput.getZipCode())
                .street(senderInput.getStreet())
                .number(senderInput.getNumber())
                .complement(senderInput.getComplement())
                .name(senderInput.getName())
                .phone(senderInput.getPhone())
                .build();

        ContactPoint recipient = ContactPoint.builder()
                .zipCode(recipientInput.getZipCode())
                .street(recipientInput.getStreet())
                .number(recipientInput.getNumber())
                .complement(recipientInput.getComplement())
                .name(recipientInput.getName())
                .phone(recipientInput.getPhone())
                .build();

        DeliveryEstimate expectedDeliveryTime = deliveryTimeEstimationService.estimate(sender, recipient);
        BigDecimal courierPayout = courierPayoutCalculationService.calculatePayout(expectedDeliveryTime.getDistancInKm());
        BigDecimal distanceFee = calculateFee(expectedDeliveryTime.getDistancInKm());

        Delivery.PreflightDetails preflightDetails = Delivery.PreflightDetails.builder()
                .recipient(recipient)
                .sender(sender)
                .expectedDeliveryTime(expectedDeliveryTime.getEstimatedTime())
                .courierPayout(courierPayout)
                .distanceFee(distanceFee)
                .build();

        delivery.editPreflightDetails(preflightDetails);

        input.getItems().forEach(item -> delivery.addItem(item.getName(), item.getQuantity()));

    }

    private BigDecimal calculateFee(Double distancInKm) {
        return new BigDecimal(3)
                .multiply(new BigDecimal(distancInKm))
                .setScale(2, RoundingMode.HALF_EVEN);
    }
}
