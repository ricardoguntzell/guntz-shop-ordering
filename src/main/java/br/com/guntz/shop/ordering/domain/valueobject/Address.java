package br.com.guntz.shop.ordering.domain.valueobject;

import br.com.guntz.shop.ordering.domain.validator.FieldValidations;
import lombok.Builder;

import java.util.Objects;

public record Address(
        String street,
        String number,
        String neighborhood,
        String city,
        String state,
        ZipCode zipCode,
        String complement
        ) {

    @Builder(toBuilder = true)
    public Address {
        FieldValidations.requiredNonBlank(street);
        FieldValidations.requiredNonBlank(number);
        FieldValidations.requiredNonBlank(neighborhood);
        FieldValidations.requiredNonBlank(city);
        FieldValidations.requiredNonBlank(state);
        Objects.requireNonNull(zipCode);
    }
}
