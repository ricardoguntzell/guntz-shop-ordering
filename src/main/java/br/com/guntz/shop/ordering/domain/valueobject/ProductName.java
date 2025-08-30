package br.com.guntz.shop.ordering.domain.valueobject;

import br.com.guntz.shop.ordering.domain.validator.FieldValidations;

public record ProductName(String value) {

    public ProductName(String value) {
        FieldValidations.requiredNonBlank(value);

        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
