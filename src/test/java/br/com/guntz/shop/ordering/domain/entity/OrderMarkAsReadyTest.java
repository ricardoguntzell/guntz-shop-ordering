package br.com.guntz.shop.ordering.domain.entity;

import br.com.guntz.shop.ordering.domain.exception.OrderStatusCannotBeChangedException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderMarkAsReadyTest {

    @Test
    public void givenDraftOrder_whenTryToMoveCorrectlyTheStatusForReady_shouldNotException() {
        Order order = OrderTestDataBuilder.anOrder().build();
        order.place();
        order.markAsPaid();

        Assertions.assertThatNoException()
                .isThrownBy(order::markAsReady);

        Assertions.assertThat(order.readyAt()).isNotNull();
    }

    @Test
    public void givenDraftOrder_whenTryToMoveIncorrectlyTheStatusForReady_shouldGenerateException() {
        Order order = OrderTestDataBuilder.anOrder().build();
        OrderStatus status = order.status();

        Assertions.assertThatExceptionOfType(OrderStatusCannotBeChangedException.class)
                .isThrownBy(order::markAsReady);

        Assertions.assertWith(order,
                o -> Assertions.assertThat(o.status()).isEqualTo(status),
                o -> Assertions.assertThat(o.readyAt()).isNull()
        );
    }

}
