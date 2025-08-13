package br.com.guntz.shop.ordering;

import br.com.guntz.shop.ordering.domain.entity.Customer;
import br.com.guntz.shop.ordering.domain.utility.IdGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.OffsetDateTime;

public class CustomerTest {

    @Test
    public void testingCustomer() {
        Customer customer = new Customer(
                IdGenerator.generateTimeBasedUUID(),
                "Guntz",
                LocalDate.of(1992, Month.AUGUST, 22),
                "guntz@guntz.com",
                "11 95550-0041",
                "123.456.789-55",
                true,
                OffsetDateTime.now()
        );

        customer.addLoyaltyPoints(10);

        System.out.println(customer.id());
        System.out.println(IdGenerator.generateTimeBasedUUID());
    }

}
