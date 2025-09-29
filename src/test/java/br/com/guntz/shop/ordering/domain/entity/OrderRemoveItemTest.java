package br.com.guntz.shop.ordering.domain.entity;

import br.com.guntz.shop.ordering.domain.exception.OrderCannotBeEditedException;
import br.com.guntz.shop.ordering.domain.exception.OrderDoesNotContainOrderItemException;
import br.com.guntz.shop.ordering.domain.valueobject.id.OrderItemId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class OrderRemoveItemTest {

    @Test
    public void givenDraftOrder_whenOneItemOrderIsRemoved_shouldVerificationTotalsOfQuantityAndAmount() {
        Order order = OrderTestDataBuilder.anOrder().build();

        OrderItemId orderItemId = order.items().stream().
                filter(orderItem -> orderItem.productName().value().contains("PS5"))
                .map(OrderItem::orderItemId)
                .findFirst()
                .get();

        System.out.println(orderItemId);
        order.removeItem(orderItemId);

        Assertions.assertThat(order.totalItems().value()).isEqualTo(3);
        Assertions.assertThat(order.totalAmount().value()).isEqualTo(new BigDecimal("13500.00"));
    }

    @Test
    public void givenDraftOrder_whenTryRemoveItemInexistent_shouldInvokeException() {
        Order order = OrderTestDataBuilder.anOrder().build();

        Assertions.assertThatExceptionOfType(OrderDoesNotContainOrderItemException.class)
                .isThrownBy(() -> order.removeItem(new OrderItemId()));
    }

    @Test
    public void givenPlaceOrder_whenTryRemoveItem_shouldInvokeException() {
        Order order = OrderTestDataBuilder.anOrder().build();
        order.place();

        Assertions.assertThatThrownBy(() -> order.removeItem(new OrderItemId()))
                .isExactlyInstanceOf(OrderCannotBeEditedException.class);
    }
}
