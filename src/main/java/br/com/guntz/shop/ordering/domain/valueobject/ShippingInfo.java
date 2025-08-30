package br.com.guntz.shop.ordering.domain.valueobject;

import lombok.Builder;

import java.util.Objects;

@Builder
public record ShippingInfo(FullName fullName,
                           Document document,
                           Phone phone,
                           Address address) {

    public ShippingInfo(FullName fullName, Document document, Phone phone, Address address) {
        this.fullName = Objects.requireNonNull(fullName);
        this.document = Objects.requireNonNull(document);
        this.phone = Objects.requireNonNull(phone);
        this.address = Objects.requireNonNull(address);
    }
}
