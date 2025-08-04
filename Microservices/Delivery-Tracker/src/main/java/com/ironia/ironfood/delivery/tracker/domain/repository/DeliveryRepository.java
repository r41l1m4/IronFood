package com.ironia.ironfood.delivery.tracker.domain.repository;

import com.ironia.ironfood.delivery.tracker.domain.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {
}
