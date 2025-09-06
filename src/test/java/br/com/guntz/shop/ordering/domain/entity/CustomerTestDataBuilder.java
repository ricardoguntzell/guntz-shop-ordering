package br.com.guntz.shop.ordering.domain.entity;

import br.com.guntz.shop.ordering.domain.valueobject.*;
import br.com.guntz.shop.ordering.domain.valueobject.id.CustomerId;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class CustomerTestDataBuilder {

    private CustomerTestDataBuilder() {
    }

    public static Customer.BrandNewCustomerBuild brandNewCustomer() {
        return Customer.brandNew()
                .fullName(new FullName("Carlos", "Villagrán"))
                .birthDate(new BirthDate(LocalDate.of(1944, 1, 12)))
                .email(new Email("kiko@chaves.com"))
                .phone(new Phone("11 12345-1234"))
                .document(new Document("806.571.170-72"))
                .promotionNotificationsAllowed(false)
                .address(Address.builder()
                        .street("Rua chavo del 8")
                        .number("8")
                        .neighborhood("Vila del chavo")
                        .city("São Paulo")
                        .state("São Paulo")
                        .zipCode(new ZipCode("12345888"))
                        .complement("casa")
                        .build());
    }

    public static Customer.ExistingCustomerBuild existingCustomer() {
        return Customer.existing()
                .id(new CustomerId())
                .registeredAt(OffsetDateTime.now())
                .promotionNotificationsAllowed(false)
                .archived(false)
                .archivedAt(null)
                .fullName(new FullName("Carlos", "Villagrán"))
                .birthDate(new BirthDate(LocalDate.of(1944, 1, 12)))
                .email(new Email("kiko@chaves.com"))
                .phone(new Phone("11 12345-1234"))
                .document(new Document("806.571.170-72"))
                .loyaltyPoints(new LoyaltyPoints(10))
                .address(Address.builder()
                        .street("Rua chavo del 8")
                        .number("8")
                        .neighborhood("Vila del chavo")
                        .city("São Paulo")
                        .state("São Paulo")
                        .zipCode(new ZipCode("12345888"))
                        .complement("casa")
                        .build());
    }

    public static Customer.ExistingCustomerBuild existingAnonymizedCustomer() {
        return Customer.existing()
                .id(new CustomerId())
                .fullName(new FullName("Anonymous", "Anonymous"))
                .birthDate(null)
                .email(new Email("kiko@chaves.com"))
                .phone(new Phone("00 90000-0000"))
                .document(new Document("806.571.170-72"))
                .promotionNotificationsAllowed(true)
                .archived(true)
                .registeredAt(OffsetDateTime.now())
                .archivedAt(OffsetDateTime.now())
                .loyaltyPoints(new LoyaltyPoints(10))
                .address(Address.builder()
                        .street("Rua chavo del 8")
                        .number("8")
                        .neighborhood("Vila del chavo")
                        .city("São Paulo")
                        .state("São Paulo")
                        .zipCode(new ZipCode("12345888"))
                        .complement("casa")
                        .build());
    }

}
