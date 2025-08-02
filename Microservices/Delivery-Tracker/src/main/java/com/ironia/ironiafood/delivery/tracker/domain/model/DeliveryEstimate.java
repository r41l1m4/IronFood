package com.ironia.ironiafood.delivery.tracker.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Duration;

@Getter
@AllArgsConstructor
public class DeliveryEstimate {

    private Duration estimatedTime;
    private Double distancInKm;
}
