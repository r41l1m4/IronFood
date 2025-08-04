package com.ironia.ironfood.delivery.tracker.domain.service;

import com.ironia.ironfood.delivery.tracker.domain.model.ContactPoint;
import com.ironia.ironfood.delivery.tracker.domain.model.DeliveryEstimate;

public interface DeliveryTimeEstimationService {

    DeliveryEstimate estimate(ContactPoint sender, ContactPoint recipient);
}
