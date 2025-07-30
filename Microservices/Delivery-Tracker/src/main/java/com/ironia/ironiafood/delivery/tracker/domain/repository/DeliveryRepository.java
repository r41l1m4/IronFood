package com.ironia.ironiafood.delivery.tracker.domain.repository;

import com.ironia.ironiafood.delivery.tracker.domain.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {
}
