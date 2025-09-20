package br.com.guntz.shop.ordering.domain.entity;


import br.com.guntz.shop.ordering.domain.exception.OrderInvalidShippingDeliveryDateException;
import br.com.guntz.shop.ordering.domain.exception.OrderStatusCannotBeChangedException;
import br.com.guntz.shop.ordering.domain.exception.ProductOutOfStockException;
import br.com.guntz.shop.ordering.domain.valueobject.*;
import br.com.guntz.shop.ordering.domain.valueobject.id.CustomerId;
import br.com.guntz.shop.ordering.domain.valueobject.id.ProductId;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

class OrderTest {

    @Test
    public void shouldGenerate() {
        Order.draft(new CustomerId());
    }

    @Test
    public void shouldAddItem() {
        Order order = Order.draft(new CustomerId());
        Product product = ProductTestDataBuilder.aProduct().build();
        ProductId productId = product.productId();

        order.addItem(
                product,
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

        order.addItem(
                ProductTestDataBuilder.aProduct().build(),
                new Quantity(1)
        );

        Set<OrderItem> items = order.items();

        Assertions.assertThatExceptionOfType(UnsupportedOperationException.class)
                .isThrownBy(items::clear);
    }

    @Test
    public void shouldCalculateTotals() {
        Order order = Order.draft(new CustomerId());

        order.addItem(
                ProductTestDataBuilder.aProduct().build(),
                new Quantity(1)
        );

        order.addItem(
                ProductTestDataBuilder.aProduct().build(),
                new Quantity(3)
        );

        Assertions.assertThat(order.totalAmount()).isEqualTo(new Money("16000"));
        Assertions.assertThat(order.totalItems()).isEqualTo(new Quantity(4));
    }

    @Test
    public void givenDraftOrder_whenPlace_shouldChangeToPlaced() {
        Order order = OrderTestDataBuilder.anOrder().build();
        order.place();

        Assertions.assertThat(order.isPlaced()).isTrue();
    }

    @Test
    public void givenPlacedOrder_whenMarkAsPaid_shouldChangeToPaid() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();
        order.markAsPaid();

        Assertions.assertThat(order.isPaid()).isTrue();
        Assertions.assertThat(order.paidAt()).isNotNull();
    }

    @Test
    public void givenPlacedOrder_whenTryToPlace_shouldGenerateException() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();

        Assertions.assertThatExceptionOfType(OrderStatusCannotBeChangedException.class)
                .isThrownBy(order::place);
    }

    @Test
    public void givenDraftOrder_whenChangePaymentMethod_shouldAllowChange() {
        Order order = Order.draft(new CustomerId());
        order.changePaymentMethod(PaymentMethod.CREDIT_CARD);

        Assertions.assertWith(order.paymentMethod()).isEqualTo(PaymentMethod.CREDIT_CARD);
    }

    @Test
    public void givenDraftOrder_whenChangeBillingInfo_shouldAllowChange() {
        Address address = Address.builder()
                .street("Anonymous street")
                .number("000")
                .neighborhood("Anonymous neighborhood")
                .city("Anonymous city")
                .state("Anonymous state")
                .zipCode(new ZipCode("12345000"))
                .complement("Anonymous complement")
                .build();

        BillingInfo billingInfo = BillingInfo.builder()
                .address(address)
                .document(new Document("806.571.170-72"))
                .phone(new Phone("11 12345-8888"))
                .fullName(new FullName("Anonymous", "Anonymous"))
                .build();

        Order order = Order.draft(new CustomerId());
        order.changeBilling(billingInfo);

        Assertions.assertThat(order.billing()).isEqualTo(billingInfo);
    }

    @Test
    public void givenDraftOrder_whenChangeShippingInfo_shouldAllowChange() {
        Address address = Address.builder()
                .street("Anonymous street")
                .number("000")
                .neighborhood("Anonymous neighborhood")
                .city("Anonymous city")
                .state("Anonymous state")
                .zipCode(new ZipCode("12345000"))
                .complement("Anonymous complement")
                .build();

        ShippingInfo shippingInfo = ShippingInfo.builder()
                .address(address)
                .document(new Document("806.571.170-72"))
                .phone(new Phone("11 12345-8888"))
                .fullName(new FullName("Anonymous", "Anonymous"))
                .build();

        Order order = Order.draft(new CustomerId());
        Money shippingCost = Money.ZERO;
        LocalDate expectedDeliveryDate = LocalDate.now().plusDays(1);

        order.changeShipping(shippingInfo, shippingCost, expectedDeliveryDate);

        Assertions.assertWith(order,
                orderSecond -> Assertions.assertThat(orderSecond.shipping()).isEqualTo(shippingInfo),
                orderSecond -> Assertions.assertThat(orderSecond.shippingCost()).isEqualTo(shippingCost),
                orderSecond -> Assertions.assertThat(orderSecond.expectedDeliveryDate()).isEqualTo(expectedDeliveryDate)
        );
    }

    @Test
    public void givenDraftOrderAndExpectedDeliveryDateInThePast_whenChangeShippingInfo_shouldNotAllowChange() {
        Address address = Address.builder()
                .street("Anonymous street")
                .number("000")
                .neighborhood("Anonymous neighborhood")
                .city("Anonymous city")
                .state("Anonymous state")
                .zipCode(new ZipCode("12345000"))
                .complement("Anonymous complement")
                .build();

        ShippingInfo shippingInfo = ShippingInfo.builder()
                .address(address)
                .document(new Document("806.571.170-72"))
                .phone(new Phone("11 12345-8888"))
                .fullName(new FullName("Anonymous", "Anonymous"))
                .build();

        Order order = Order.draft(new CustomerId());
        Money shippingCost = Money.ZERO;
        LocalDate expectedDeliveryDate = LocalDate.now().minusDays(2);

        Assertions.assertThatExceptionOfType(OrderInvalidShippingDeliveryDateException.class)
                .isThrownBy(() -> order.changeShipping(shippingInfo, shippingCost, expectedDeliveryDate));
    }

    @Test
    public void givenDraftOrder_whenChangeItem_shouldRecalculate() {
        Order order = Order.draft(new CustomerId());

        order.addItem(
                ProductTestDataBuilder.aProductCanecaPsyTrance().build(),
                new Quantity(3)
        );

        OrderItem orderItem = order.items().iterator().next();

        order.changeItemQuantity(orderItem.id(), new Quantity(5));

        Assertions.assertWith(order,
                o -> Assertions.assertThat(o.totalAmount()).isEqualTo(new Money("400")),
                o -> Assertions.assertThat(o.totalItems()).isEqualTo(new Quantity(5))
        );
    }

    @Test
    public void givenOutOfStockProduct_whenTryToAddToAnOrder_shouldNotAllow() {
        Order order = Order.draft(new CustomerId());

        ThrowableAssert.ThrowingCallable addItemTask =
                () -> order.addItem(ProductTestDataBuilder.aProductUnavailable().build(), new Quantity(1));

        Assertions.assertThatExceptionOfType(ProductOutOfStockException.class)
                .isThrownBy(addItemTask);
    }
}