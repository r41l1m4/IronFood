package com.ironia.ironiafood.delivery.tracker.domain.service;

import java.math.BigDecimal;

public interface CourierPayoutCalculationService {

    BigDecimal calculatePayout(Double distanceInKm);
}
