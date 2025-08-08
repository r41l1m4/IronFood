package com.ironia.ironfood.delivery.tracker.infrastructure.http.client;

import com.ironia.ironfood.delivery.tracker.domain.service.CourierPayoutCalculationService;
import com.ironia.ironfood.delivery.tracker.infrastructure.http.client.exceptions.BadGatewayException;
import com.ironia.ironfood.delivery.tracker.infrastructure.http.client.exceptions.GatewayTimeoutException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CourierPayoutCalculationServiceHttpImpl implements CourierPayoutCalculationService {

    private final CourierAPIClient courierAPIClient;

    @Override
    public BigDecimal calculatePayout(Double distanceInKm) {
        try {
            CourierPayoutResultModel courierPayoutResultModel =  courierAPIClient.payoutCalculation(
                    new CourierPayoutCalculationInput(distanceInKm));

            return courierPayoutResultModel.getPayoutFee();
        } catch (ResourceAccessException e) {
            throw new GatewayTimeoutException(e);
        }catch (HttpServerErrorException | IllegalArgumentException e) {
            throw new BadGatewayException(e);
        }
    }
}
