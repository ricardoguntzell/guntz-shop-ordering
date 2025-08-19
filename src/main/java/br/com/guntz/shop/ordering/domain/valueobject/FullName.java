package br.com.guntz.shop.ordering.domain.valueobject;

import java.util.Objects;

import static br.com.guntz.shop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_FULLNAME_IS_BLANK;

public record FullName(String firstName, String lastName) {

    public FullName(String firstName, String lastName) {
        Objects.requireNonNull(firstName);
        Objects.requireNonNull(lastName);

        if (firstName.isBlank() || lastName.isBlank()) {
            throw new IllegalArgumentException(VALIDATION_ERROR_FULLNAME_IS_BLANK);
        }

        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
    }

    @Override
    public String toString() {
        return firstName
                .concat(" ")
                .concat(lastName);
    }
}
