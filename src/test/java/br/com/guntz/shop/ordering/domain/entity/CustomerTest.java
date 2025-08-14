package br.com.guntz.shop.ordering.domain.entity;

import br.com.guntz.shop.ordering.domain.utility.IdGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;

class CustomerTest {

    @Test
    void given_invalidEmail_whenTryCreateCustomer_shouldGenerateException() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(CustomerTest::correctCustomerModel);
    }

    @Test
    void given_invalidEmail_whenTryUpdatedCustomerEmail_shouldGenerateException() {
        Customer customer = correctCustomerModel();

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> {
                    customer.changeEmail("invalid");
                });
    }


    @Test
    void given_unarchivedCustomer_whenArchive_shouldAnonymize() {
        Customer customer = correctCustomerModel();

        customer.archive();

        Assertions.assertWith(customer,
        c -> Assertions.assertThat(c.fullName()).isEqualTo("Anonymous"),
        c -> Assertions.assertThat(c.email()).isNotEqualTo("guntz@mail.com"),
        c -> Assertions.assertThat(c.phone()).isEqualTo("00 90000-0000"),
        c -> Assertions.assertThat(c.document()).isEqualTo("000.000.000-00"),
        c -> Assertions.assertThat(c.birthDate()).isNull()

        );
    }

    private static Customer correctCustomerModel() {
        return new Customer(
                IdGenerator.generateTimeBasedUUID(),
                "Guntz",
                LocalDate.of(1992, 8, 22),
                "guntz@mail.com",
                "11 95550-0041",
                "123.456.789-55",
                false,
                OffsetDateTime.now()
        );
    }

}