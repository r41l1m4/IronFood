package com.ironia.ironfood.delivery.tracker.infrastructure.http.client.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.GATEWAY_TIMEOUT)
public class GatewayTimeoutException extends RuntimeException{
    public GatewayTimeoutException() {
    }

    public GatewayTimeoutException(Throwable cause) {
        super(cause);
    }
}
