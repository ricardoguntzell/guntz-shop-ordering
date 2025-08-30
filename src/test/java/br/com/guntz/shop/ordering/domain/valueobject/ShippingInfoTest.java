package br.com.guntz.shop.ordering.domain.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ShippingInfoTest {

    @Test
    public void given_NewShippingInfo_whenAddValidShippingInfo_shouldNotGenerateException() {
        Assertions.assertThatNoException()
                .isThrownBy(() ->
                        ShippingInfo.builder()
                                .fullName(new FullName("Anonnymous", "Anonnymous"))
                                .document(new Document("806.571.170-72"))
                                .phone(new Phone("11 91234-5555"))
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
    public void given_NewShippingInfo_whenAddInvalidShippingInfo_shouldGenerateException() {
        Assertions.assertThatExceptionOfType(Exception.class)
                .isThrownBy(() ->
                        ShippingInfo.builder()
                                .fullName(null)
                                .document(null)
                                .phone(null)
                                .address(null)
                                .build()
                );

        Assertions.assertThatExceptionOfType(Exception.class)
                .isThrownBy(() ->
                        ShippingInfo.builder()
                                .fullName(new FullName("", ""))
                                .document(new Document(""))
                                .phone(new Phone(""))
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