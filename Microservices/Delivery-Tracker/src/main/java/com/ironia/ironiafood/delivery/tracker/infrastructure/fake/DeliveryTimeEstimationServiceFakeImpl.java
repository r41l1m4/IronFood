package com.ironia.ironiafood.delivery.tracker.infrastructure.fake;

import com.ironia.ironiafood.delivery.tracker.domain.model.ContactPoint;
import com.ironia.ironiafood.delivery.tracker.domain.model.DeliveryEstimate;
import com.ironia.ironiafood.delivery.tracker.domain.service.DeliveryTimeEstimationService;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class DeliveryTimeEstimationServiceFakeImpl implements DeliveryTimeEstimationService {

    @Override
    public DeliveryEstimate estimate(ContactPoint sender, ContactPoint recipient) {
        return new DeliveryEstimate(Duration.ofHours(3), 3.1);
    }
}
