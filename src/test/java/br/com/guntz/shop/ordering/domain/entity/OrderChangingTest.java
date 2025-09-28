package br.com.guntz.shop.ordering.domain.entity;

import br.com.guntz.shop.ordering.domain.exception.OrderCannotBeEditedException;
import br.com.guntz.shop.ordering.domain.valueobject.Billing;
import br.com.guntz.shop.ordering.domain.valueobject.Product;
import br.com.guntz.shop.ordering.domain.valueobject.Quantity;
import br.com.guntz.shop.ordering.domain.valueobject.Shipping;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderChangingTest {

    @Test
    public void givenDraftOrder_whenChanged_shouldNotException() {
        Order order = OrderTestDataBuilder.anOrder().build();
        Product product = ProductTestDataBuilder.aProduct().build();
        Shipping shipping = OrderTestDataBuilder.aShipping();
        Billing billing = OrderTestDataBuilder.aBilling();

        Assertions.assertWith(order,
                o -> Assertions.assertThatNoException().isThrownBy(
                        () -> order.changePaymentMethod(PaymentMethod.CREDIT_CARD)),
                o -> Assertions.assertThatNoException().isThrownBy(
                        () -> order.addItem(product, new Quantity(1))),
                o -> Assertions.assertThatNoException().isThrownBy(
                        () -> order.changeShipping(shipping)),
                o -> Assertions.assertThatNoException().isThrownBy(
                        () -> order.changeBilling(billing))
        );
    }

    @Test
    public void givenDraftOrder_WhenToChangeTheOrderAfterSimulatingSStatusTransition_shouldInvokeException() {
        Order order = OrderTestDataBuilder.anOrder().build();
        order.place();

        Assertions.assertThatThrownBy(() -> order.changePaymentMethod(PaymentMethod.CREDIT_CARD))
                .isExactlyInstanceOf(OrderCannotBeEditedException.class);
    }

    @Test
    public void givenNotDraftOrder_whenChanged_shouldInvokeException() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PAID).build();
        Product product = ProductTestDataBuilder.aProduct().build();
        Shipping shipping = OrderTestDataBuilder.aShipping();
        Billing billing = OrderTestDataBuilder.aBilling();

        Assertions.assertThatThrownBy(() -> order.changePaymentMethod(PaymentMethod.CREDIT_CARD))
                .isExactlyInstanceOf(OrderCannotBeEditedException.class);

        Assertions.assertThatThrownBy(() -> order.addItem(product, new Quantity(1)))
                .isExactlyInstanceOf(OrderCannotBeEditedException.class);

        Assertions.assertThatThrownBy(() -> order.changeShipping(shipping))
                .isExactlyInstanceOf(OrderCannotBeEditedException.class);

        Assertions.assertThatThrownBy(() -> order.changeBilling(billing))
                .isExactlyInstanceOf(OrderCannotBeEditedException.class);

    }
}
