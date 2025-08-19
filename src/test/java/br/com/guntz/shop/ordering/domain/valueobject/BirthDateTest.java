package br.com.guntz.shop.ordering.domain.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Period;

class BirthDateTest {

    @Test
    void given_NewBirthDate_whenAddInvalidBirthDate_shouldGenerateException() {
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> {
                    new BirthDate(null);
                });

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> {
                    new BirthDate(LocalDate.of(2992, 8, 22));
                });
    }

    @Test
    void given_newBirthDate_whenAgeSolicited_shouldCalculateAge() {
        BirthDate birthDate = new BirthDate(LocalDate.of(1992, 8, 22));

        Assertions.assertThat(birthDate.age()).isEqualTo(Period
                .between(LocalDate.now(), birthDate.birthDate())
                .getYears());
    }


}