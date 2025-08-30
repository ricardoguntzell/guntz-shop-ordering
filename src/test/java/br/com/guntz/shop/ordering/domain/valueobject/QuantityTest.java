package br.com.guntz.shop.ordering.domain.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class QuantityTest {

    @Test
    public void given_NewQuantity_whenAddValidQuantity_shouldNotGenerateException() {
        Assertions.assertThatNoException()
                .isThrownBy(() -> new Quantity(2));

        Assertions.assertThatNoException()
                .isThrownBy(() -> new Quantity(2)
                        .add(new Quantity(2)));
    }

    @Test
    public void given_NewQuantity_whenAddInvalidQuantity_shouldNotGenerateException() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Quantity(-1));

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Quantity(2)
                        .add(new Quantity(-2)));
    }
}