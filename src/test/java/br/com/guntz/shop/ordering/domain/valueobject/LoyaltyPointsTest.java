package br.com.guntz.shop.ordering.domain.valueobject;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class LoyaltyPointsTest {

    @Test
    void shouldGenerateWithValue() {
        var valuePoints = 10;
        LoyaltyPoints loyaltyPoints = new LoyaltyPoints(valuePoints);

        Assertions.assertThat(loyaltyPoints.points()).isEqualTo(valuePoints);
    }

    @Test
    void shouldAddValue() {
        LoyaltyPoints loyaltyPoints = new LoyaltyPoints(10);

        Assertions.assertThat(loyaltyPoints.add(50).points()).isEqualTo(60);
    }

    @Test
    void shouldNotAddValue() {
        LoyaltyPoints loyaltyPoints = new LoyaltyPoints(10);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> loyaltyPoints.add(-1));

        Assertions.assertThat(loyaltyPoints.points()).isEqualTo(10);
    }

    @Test
    void shouldNotZeroValue() {
        LoyaltyPoints loyaltyPoints = new LoyaltyPoints(10);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> loyaltyPoints.add(0));

        Assertions.assertThat(loyaltyPoints.points()).isEqualTo(10);
    }

}