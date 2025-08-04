package com.ironia.ironfood.courier.manager.api.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourierInput {

    @NotBlank
    private String name;

    @NotBlank
    private String phone;
}
