package br.com.guntz.shop.ordering.domain.valueobject;

import static br.com.guntz.shop.ordering.domain.exception.ErrorMessages.ERROR_QUANTITY;

public record Quantity(Integer value) implements Comparable<Quantity> {

    public static final Quantity ZERO = new Quantity(0);

    public Quantity(Integer value) {
        if (value < 0) {
            throw new IllegalArgumentException(ERROR_QUANTITY);
        }

        this.value = value;
    }

    public Quantity add(Quantity other) {
        return new Quantity(value + other.value);
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    @Override
    public int compareTo(Quantity other) {
        return this.value.compareTo(other.value());
    }
}
