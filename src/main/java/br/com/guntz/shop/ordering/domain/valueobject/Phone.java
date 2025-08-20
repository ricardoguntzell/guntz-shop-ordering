package br.com.guntz.shop.ordering.domain.valueobject;

import java.util.Objects;

import static br.com.guntz.shop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_PHONE_IS_BLANK;

public record Phone(String value) {

    public Phone(String value) {
        Objects.requireNonNull(value);

        if (value.isBlank()) {
            throw new IllegalArgumentException(VALIDATION_ERROR_PHONE_IS_BLANK);
        }

        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
