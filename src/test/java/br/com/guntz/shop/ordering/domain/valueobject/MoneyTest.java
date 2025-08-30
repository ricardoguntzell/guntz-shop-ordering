package br.com.guntz.shop.ordering.domain.valueobject;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class MoneyTest {

    @Test
    public void given_NewMoney_whenAddValidMoney_shouldNotGenerateException() {
        Assertions.assertThatNoException()
                .isThrownBy(() -> new Money(new BigDecimal(1)));

        Assertions.assertThatNoException()
                .isThrownBy(() -> new Money(new BigDecimal(0)));

        Assertions.assertThatNoException()
                .isThrownBy(() -> new Money(new BigDecimal("1")));

        Assertions.assertThatNoException()
                .isThrownBy(() -> new Money(new BigDecimal("0")));

        Assertions.assertThatNoException()
                .isThrownBy(() -> new Money(new BigDecimal("2.50"))
                        .add(new Money("5")));

        Assertions.assertThatNoException()
                .isThrownBy(() -> new Money(new BigDecimal("2.50"))
                        .multiply(new Quantity(2)));

        Assertions.assertThatNoException()
                .isThrownBy(() -> new Money(new BigDecimal("2.50"))
                        .divide(new Money(new BigDecimal(2))));
    }

    @Test
    public void given_NewMoney_whenMultiplyValidMoney_shouldNotGenerateException() {
        Money money = new Money("1.50");
        Quantity quantity = new Quantity(2);

        Assertions.assertThatNoException()
                .isThrownBy(() -> money.multiply(quantity));
    }

    @Test
    public void given_NewMoney_whenMultiplyMoneyInvalidQuantity_shouldGenerateException() {
        Money money = new Money("1.50");

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> money.multiply(new Quantity(-1)));
    }

    @Test
    public void given_NewMoney_whenAddInvalidMoney_shouldGenerateException() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Money(""));

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Money(new BigDecimal("-1")));

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Money(new BigDecimal(-1)));

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Money(new BigDecimal("2.50"))
                        .add(new Money("")));

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Money(new BigDecimal("2.50"))
                        .multiply(new Quantity(-1)));

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Money(new BigDecimal("2.50"))
                        .multiply(new Quantity(0)));

        Assertions.assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(() -> new Money(new BigDecimal("2.50"))
                        .divide(new Money(new BigDecimal(0))));

    }

}