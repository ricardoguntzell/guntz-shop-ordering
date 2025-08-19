package br.com.guntz.shop.ordering.domain.valueobject;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

import static br.com.guntz.shop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_BIRTHDATE_MUST_IN_PAST;

public record BirthDate(LocalDate birthDate) {

    public BirthDate(LocalDate birthDate) {
        Objects.requireNonNull(birthDate);

        if (birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(VALIDATION_ERROR_BIRTHDATE_MUST_IN_PAST);
        }

        this.birthDate = birthDate;
    }

    public Integer age() {
        return Period
                .between(LocalDate.now(), birthDate)
                .getYears();
    }

    @Override
    public String toString() {
        return birthDate.toString();
    }
}
