package com.ironia.ironiafood.delivery.tracker.domain.service;

import com.ironia.ironiafood.delivery.tracker.domain.model.ContactPoint;
import com.ironia.ironiafood.delivery.tracker.domain.model.DeliveryEstimate;

public interface DeliveryTimeEstimationService {

    DeliveryEstimate estimate(ContactPoint sender, ContactPoint recipient);
}
