package br.com.guntz.shop.ordering.domain.valueobject;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ProductNameTest {

    @Test
    public void given_NewProductName_whenAddValidProductName_shouldNotGenerateException() {
        Assertions.assertThatNoException()
                .isThrownBy(() -> new ProductName("PS5 Slim"));

    }

    @Test
    public void given_NewProductName_whenAddInvalidProductName_shouldGenerateException() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new ProductName(""));

        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new ProductName(null));

    }

}