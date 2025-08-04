package com.ironia.ironfood.courier.manager.domain.repository;

import com.ironia.ironfood.courier.manager.domain.model.Courier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourierRepository extends JpaRepository<Courier, UUID> {
}
