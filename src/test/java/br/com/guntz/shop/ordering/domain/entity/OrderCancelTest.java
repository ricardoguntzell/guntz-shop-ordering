package br.com.guntz.shop.ordering.domain.entity;

import br.com.guntz.shop.ordering.domain.exception.OrderStatusCannotBeChangedException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

public class OrderCancelTest {

    @Test
    public void givenDraftOrder_whenTryToMoveTheStatusForCanceled_shouldNotException() {
        Order order = OrderTestDataBuilder.anOrder().build();
        Assertions.assertWith(order,
                o -> Assertions.assertThatNoException().isThrownBy(order::cancel),
                o -> Assertions.assertThat(OrderStatus.CANCELED).isEqualTo(order.status()),
                o -> Assertions.assertThat(order.canceledAt()).isNotNull()
        );
    }

    @Test
    public void givenPlacedtOrder_whenTryToMoveTheStatusForCanceled_shouldNotException() {
        Order order = OrderTestDataBuilder.anOrder().build();
        order.place();

        Assertions.assertWith(order,
                o -> Assertions.assertThatNoException().isThrownBy(order::cancel),
                o -> Assertions.assertThat(OrderStatus.CANCELED).isEqualTo(order.status()),
                o -> Assertions.assertThat(order.canceledAt()).isNotNull()
        );
    }

    @Test
    public void givenPaidtOrder_whenTryToMoveTheStatusForCanceled_shouldNotException() {
        Order order = OrderTestDataBuilder.anOrder().build();
        order.place();
        order.markAsPaid();

        Assertions.assertWith(order,
                o -> Assertions.assertThatNoException().isThrownBy(order::cancel),
                o -> Assertions.assertThat(OrderStatus.CANCELED).isEqualTo(order.status()),
                o -> Assertions.assertThat(order.canceledAt()).isNotNull()
        );
    }


    @Test
    public void givenReadytOrder_whenTryToMoveTheStatusForCanceled_shouldNotException() {
        Order order = OrderTestDataBuilder.anOrder().build();
        order.place();
        order.markAsPaid();

        Assertions.assertWith(order,
                o -> Assertions.assertThatNoException().isThrownBy(order::cancel),
                o -> Assertions.assertThat(OrderStatus.CANCELED).isEqualTo(order.status()),
                o -> Assertions.assertThat(order.canceledAt()).isNotNull()
        );
    }


    @Test
    public void givenCanceledOrder_whenTryChangedToStatusForCancelAgain_shouldGenerateException() {
        Order order = OrderTestDataBuilder.anOrder().build();
        order.cancel();
        OrderStatus currentStatus = order.status();
        OffsetDateTime currentCanceledAt = order.canceledAt();

        Assertions.assertThatExceptionOfType(OrderStatusCannotBeChangedException.class)
                .isThrownBy(order::cancel);

        Assertions.assertWith(order,
                o -> Assertions.assertThat(currentStatus).isEqualTo(o.status()),
                o -> Assertions.assertThat(currentCanceledAt).isEqualTo(o.canceledAt())
        );
    }

    @Test
    public void givenCanceledOrder_whenTryVerifyToStatus_shouldReturnTrue() {
        Order order = OrderTestDataBuilder.anOrder().build();
        order.cancel();

        Assertions.assertThat(Boolean.TRUE).isEqualTo(order.isCanceled());
    }

    @Test
    public void givenNotCanceledOrder_whenTryVerifyToStatus_shouldReturnFalse() {
        Order order = OrderTestDataBuilder.anOrder().build();

        Assertions.assertThat(Boolean.FALSE).isEqualTo(order.isCanceled());
    }

}
