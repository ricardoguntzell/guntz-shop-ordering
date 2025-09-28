package br.com.guntz.shop.ordering.domain.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class BillingTest {

    @Test
    public void given_NewBillingInfo_whenAddValidBillingInfo_shouldNotGenerateException() {
        Assertions.assertThatNoException()
                .isThrownBy(() ->
                        Billing.builder()
                                .fullName(new FullName("Anonnymous", "Anonnymous"))
                                .document(new Document("806.571.170-72"))
                                .phone(new Phone("11 91234-5555"))
                                .email(new Email("anonymous.admin@shop.com.br"))
                                .address(Address.builder()
                                        .street("Rua chavo del 8")
                                        .number("Anonymous")
                                        .neighborhood("Vila del chavo")
                                        .city("São Paulo")
                                        .state("São Paulo")
                                        .zipCode(new ZipCode("12345888"))
                                        .complement(null)
                                        .build()
                                )
                                .build()
                );
    }

    @Test
    public void given_NewBillingInfo_whenAddInvalidBillingInfo_shouldGenerateException() {
        Assertions.assertThatExceptionOfType(Exception.class)
                .isThrownBy(() ->
                        Billing.builder()
                                .fullName(null)
                                .document(null)
                                .phone(null)
                                .address(null)
                                .build()
                );

        Assertions.assertThatExceptionOfType(Exception.class)
                .isThrownBy(() ->
                        Billing.builder()
                                .fullName(new FullName("", ""))
                                .document(new Document(""))
                                .phone(new Phone(""))
                                .email(new Email(""))
                                .address(Address.builder()
                                        .street("")
                                        .number("")
                                        .neighborhood("")
                                        .city("")
                                        .state("")
                                        .zipCode(new ZipCode(""))
                                        .complement(null)
                                        .build()
                                )
                                .build()
                );
    }
}