package br.com.guntz.shop.ordering.domain.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import static br.com.guntz.shop.ordering.domain.exception.ErrorMessages.ERROR_MONEY;
import static br.com.guntz.shop.ordering.domain.exception.ErrorMessages.ERROR_MONEY_QUANTITY_ZERO;

public record Money(BigDecimal value) implements Comparable<Money> {

    public static final RoundingMode ROUNDING_MODE_DEFAULT = RoundingMode.HALF_EVEN;
    public static final Money ZERO = new Money(BigDecimal.ZERO);
    private static final int SCALE_DAFAULT = 2;

    public Money(String value) {
        this(new BigDecimal(value));
    }

    public Money(BigDecimal value) {
        isCorrectMoney(value);

        this.value = value.setScale(SCALE_DAFAULT, ROUNDING_MODE_DEFAULT);
    }

    public Money multiply(Quantity quantity) {
        if (isAValidAmountForMultiplying(quantity)) {
            throw new IllegalArgumentException(ERROR_MONEY_QUANTITY_ZERO);
        }

        return new Money(value.multiply(BigDecimal.valueOf(quantity.value())));
    }

    public Money add(Money other) {
        return new Money(value.add(other.value));
    }

    public Money divide(Money other) {
        return new Money(value.divide(other.value, ROUNDING_MODE_DEFAULT));
    }

    private void isCorrectMoney(BigDecimal value) {
        Objects.requireNonNull(value, () -> {
            throw new IllegalArgumentException(ERROR_MONEY);
        });

        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(ERROR_MONEY);
        }
    }

    private boolean isAValidAmountForMultiplying(Quantity quantity) {
        return quantity.compareTo(Quantity.ZERO) <= 0;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    @Override
    public int compareTo(Money other) {
        return this.value.compareTo(other.value());
    }
}
