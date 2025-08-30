package br.com.guntz.shop.ordering.domain.valueobject;

import lombok.Builder;

import java.util.Objects;

@Builder
public record BillingInfo(FullName fullName,
                          Document document,
                          Phone phone,
                          Address address) {

    public BillingInfo(FullName fullName, Document document, Phone phone, Address address) {
        this.fullName = Objects.requireNonNull(fullName);
        this.document = Objects.requireNonNull(document);
        this.phone = Objects.requireNonNull(phone);
        this.address = Objects.requireNonNull(address);
    }
}
