package br.com.guntz.shop.ordering.domain.entity;

import br.com.guntz.shop.ordering.domain.exception.CustomerArchivedException;
import br.com.guntz.shop.ordering.domain.valueobject.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;

class CustomerTest {

    @Test
    void given_invalidEmail_whenTryCreateCustomer_shouldGenerateException() {
        Customer customer = correctCustomerModel();

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> customer.changeEmail("invalid"));
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
                c -> Assertions.assertThat(c.fullName()).isEqualTo(new FullName("Anonymous", "Anonymous")),
                c -> Assertions.assertThat(c.email()).isNotEqualTo("guntz@mail.com"),
                c -> Assertions.assertThat(c.phone()).isEqualTo("00 90000-0000"),
                c -> Assertions.assertThat(c.document()).isEqualTo(new Document("806.571.170-72")),
                c -> Assertions.assertThat(c.birthDate()).isNull()

        );
    }

    @Test
    void given_archivedCustomer_whenTryToUpdate_shouldGenerateException() {
        Customer customer = correctCustomerModel();
        customer.archive();

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(customer::archive);

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> customer.changeEmail("anonymous@anonymous.com"));

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> customer.changePhone("11 98877-4455"));

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(customer::disablePromotionNotifications);

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(customer::enablePromotionNotifications);
    }

    @Test
    void given_brandNewCustomer_whenAddLoyaltyPoints_shouldSumPoints() {
        Customer customer = correctCustomerModel();

        customer.addLoyaltyPoints(new LoyaltyPoints(10));
        customer.addLoyaltyPoints(new LoyaltyPoints(20));

        Assertions.assertThat(customer.loyaltyPoints()).isEqualTo(new LoyaltyPoints(30));
    }

    @Test
    void given_brandNewCustomer_whenAddInvalidLoyaltyPoints_shouldGenerateException() {
        Customer customer = correctCustomerModel();

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> customer.addLoyaltyPoints(LoyaltyPoints.ZERO));

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> customer.addLoyaltyPoints(new LoyaltyPoints(-50)));
    }

    private static Customer correctCustomerModel() {
        return new Customer(
                new CustomerId(),
                new FullName("Carlos", "Villagrán"),
                new BirthDate(LocalDate.of(1944, 1, 12)),
                "kiko@chaves.com",
                "11 12345-1234",
                new Document("806.571.170-72"),
                false,
                OffsetDateTime.now()
        );
    }

}