package com.ironia.ironfood.delivery.tracker.infrastructure.http.client;

import com.ironia.ironfood.delivery.tracker.domain.service.CourierPayoutCalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CourierPayoutCalculationServiceHttpImpl implements CourierPayoutCalculationService {

    private final CourierAPIClient courierAPIClient;

    @Override
    public BigDecimal calculatePayout(Double distanceInKm) {
        CourierPayoutResultModel courierPayoutResultModel =  courierAPIClient.payoutCalculation(
                new CourierPayoutCalculationInput(distanceInKm));

        return courierPayoutResultModel.getPayoutFee();
    }
}
