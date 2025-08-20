package br.com.guntz.shop.ordering.domain.valueobject;

import br.com.guntz.shop.ordering.domain.validator.FieldValidations;

import static br.com.guntz.shop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_EMAIL_IS_INVALID;

public record Email(String value) {

    public Email(String value) {
        FieldValidations.requiresValidEmail(value, VALIDATION_ERROR_EMAIL_IS_INVALID);

        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
