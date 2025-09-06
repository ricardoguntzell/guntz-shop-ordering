package br.com.guntz.shop.ordering.domain.entity;


import br.com.guntz.shop.ordering.domain.exception.OrderStatusCannotBeChangedException;
import br.com.guntz.shop.ordering.domain.valueobject.Money;
import br.com.guntz.shop.ordering.domain.valueobject.ProductName;
import br.com.guntz.shop.ordering.domain.valueobject.Quantity;
import br.com.guntz.shop.ordering.domain.valueobject.id.CustomerId;
import br.com.guntz.shop.ordering.domain.valueobject.id.ProductId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

class OrderTest {

    @Test
    public void shouldGenerate() {
        Order.draft(new CustomerId());
    }

    @Test
    public void shouldAddItem() {
        Order order = Order.draft(new CustomerId());
        ProductId productId = new ProductId();

        order.addItem(
                productId,
                new ProductName("PS5"),
                new Money("4000"),
                new Quantity(1)
        );

        Assertions.assertThat(order.items()).size().isEqualTo(1);

        OrderItem orderItem = order.items().iterator().next();

        Assertions.assertWith(orderItem,
                (i) -> Assertions.assertThat(i.id()).isNotNull(),
                (i) -> Assertions.assertThat(i.productName()).isEqualTo(new ProductName("PS5")),
                (i) -> Assertions.assertThat(i.productId()).isEqualTo(productId),
                (i) -> Assertions.assertThat(i.price()).isEqualTo(new Money("4000")),
                (i) -> Assertions.assertThat(i.quantity()).isEqualTo(new Quantity(1))
        );
    }

    @Test
    public void shouldGenerateExecptionWhenTryToChangeItemSet() {
        Order order = Order.draft(new CustomerId());
        ProductId productId = new ProductId();

        order.addItem(
                productId,
                new ProductName("PS5"),
                new Money("4000"),
                new Quantity(1)
        );

        Set<OrderItem> items = order.items();

        Assertions.assertThatExceptionOfType(UnsupportedOperationException.class)
                .isThrownBy(items::clear);
    }

    @Test
    public void shouldCalculateTotals() {
        Order order = Order.draft(new CustomerId());
        ProductId productId = new ProductId();

        order.addItem(
                productId,
                new ProductName("PS5"),
                new Money("4000"),
                new Quantity(1)
        );

        order.addItem(
                productId,
                new ProductName("Iphone 14"),
                new Money("4200"),
                new Quantity(3)
        );

        Assertions.assertThat(order.totalAmount()).isEqualTo(new Money("16600"));
        Assertions.assertThat(order.totalItems()).isEqualTo(new Quantity(4));
    }

    @Test
    public void givenDraftOrder_whenPlace_shouldChangeToPlaced() {
        Order order = Order.draft(new CustomerId());
        order.place();

        Assertions.assertThat(order.isPlaced()).isTrue();
    }

    @Test
    public void givenPlacedOrder_whenTryToPlace_shouldGenerateException() {
        Order order = Order.draft(new CustomerId());
        order.place();

        Assertions.assertThatExceptionOfType(OrderStatusCannotBeChangedException.class)
                .isThrownBy(order::place);
    }

}