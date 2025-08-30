package br.com.guntz.shop.ordering.domain.valueobject;

import br.com.guntz.shop.ordering.domain.validator.FieldValidations;

import static br.com.guntz.shop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_PHONE_IS_BLANK;

public record Phone(String value) {

    public Phone(String value) {
        FieldValidations.requiredNonBlank(value, VALIDATION_ERROR_PHONE_IS_BLANK);

        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
