package br.com.guntz.shop.ordering.domain.valueobject;

import java.util.Objects;

import static br.com.guntz.shop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_LOYALTY_POINTS_IS_NEGATIVE;
import static br.com.guntz.shop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_LOYALTY_POINTS_IS_ZERO;

public record LoyaltyPoints(Integer points) implements Comparable<LoyaltyPoints> {

    public static final LoyaltyPoints ZERO = new LoyaltyPoints(0);

    public LoyaltyPoints() {
        this(0);
    }

    public LoyaltyPoints(Integer points) {
        Objects.requireNonNull(points);

        if (points < 0) {
            throw new IllegalArgumentException();
        }

        this.points = points;
    }

    public LoyaltyPoints add(Integer value) {
        return add(new LoyaltyPoints(value));
    }

    public LoyaltyPoints add(LoyaltyPoints loyaltyPoints) {
        Objects.requireNonNull(loyaltyPoints);

        if (loyaltyPoints.points() == 0) {
            throw new IllegalArgumentException(VALIDATION_ERROR_LOYALTY_POINTS_IS_ZERO);
        }

        if (loyaltyPoints.points() < 0) {
            throw new IllegalArgumentException(VALIDATION_ERROR_LOYALTY_POINTS_IS_NEGATIVE);
        }

        return new LoyaltyPoints(this.points() + loyaltyPoints.points);
    }

    @Override
    public String toString() {
        return points.toString();
    }

    @Override
    public int compareTo(LoyaltyPoints loyaltyPoints) {
        return this.points().compareTo(loyaltyPoints.points);
    }
}
