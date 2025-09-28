package br.com.guntz.shop.ordering.domain.valueobject;

import lombok.Builder;

import java.util.Objects;

@Builder
public record Billing(FullName fullName,
                      Document document,
                      Phone phone,
                      Email email,
                      Address address) {

    public Billing(FullName fullName, Document document, Phone phone, Email email, Address address) {
        this.fullName = Objects.requireNonNull(fullName);
        this.document = Objects.requireNonNull(document);
        this.phone = Objects.requireNonNull(phone);
        this.email = Objects.requireNonNull(email);
        this.address = Objects.requireNonNull(address);
    }
}
