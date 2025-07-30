package com.ironia.ironiafood.delivery.tracker.domain.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class ContactPoint {
    private String zipCode;
    private String street;
    private String number;
    private String complement;
    private String name;
    private String phone;
}
