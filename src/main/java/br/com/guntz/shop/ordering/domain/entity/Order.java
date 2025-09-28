package br.com.guntz.shop.ordering.domain.entity;

import br.com.guntz.shop.ordering.domain.exception.*;
import br.com.guntz.shop.ordering.domain.valueobject.*;
import br.com.guntz.shop.ordering.domain.valueobject.id.CustomerId;
import br.com.guntz.shop.ordering.domain.valueobject.id.OrderId;
import br.com.guntz.shop.ordering.domain.valueobject.id.OrderItemId;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
public class Order {

    private OrderId id;
    private CustomerId customerId;

    private Money totalAmount;
    private Quantity totalItems;

    private OffsetDateTime placedAt;
    private OffsetDateTime paidAt;
    private OffsetDateTime canceledAt;
    private OffsetDateTime readyAt;

    private Billing billing;
    private Shipping shipping;

    private OrderStatus status;
    private PaymentMethod paymentMethod;

    private Set<OrderItem> items;

    @Builder(builderClassName = "ExistingOrderBuilder", builderMethodName = "existing")
    public Order(OrderId id, CustomerId customerId, Money totalAmount, Quantity totalItems, OffsetDateTime placedAt,
                 OffsetDateTime paidAt, OffsetDateTime canceledAt, OffsetDateTime readyAt, Billing billing,
                 Shipping shipping, OrderStatus status, PaymentMethod paymentMethod, Set<OrderItem> items) {

        this.setId(id);
        this.setCustomerId(customerId);
        this.setTotalAmount(totalAmount);
        this.setTotalItems(totalItems);
        this.setPlacedAt(placedAt);
        this.setPaidAt(paidAt);
        this.setCanceledAt(canceledAt);
        this.setReadyAt(readyAt);
        this.setBilling(billing);
        this.setShipping(shipping);
        this.setStatus(status);
        this.setPaymentMethod(paymentMethod);
        this.setItems(items);
    }

    public static Order draft(CustomerId customerId) {
        return new Order(
                new OrderId(),
                customerId,
                Money.ZERO,
                Quantity.ZERO,
                null,
                null,
                null,
                null,
                null,
                null,
                OrderStatus.DRAFT,
                null,
                new HashSet<>()
        );
    }

    public void addItem(Product product, Quantity quantity) {
        this.verifyIfChangeable();

        Objects.requireNonNull(product);
        Objects.requireNonNull(quantity);

        product.checkOutOfStock();

        OrderItem orderItem = OrderItem.brandNew()
                .orderId(this.id)
                .product(product)
                .quantity(quantity)
                .build();

        if (this.items.isEmpty()) {
            this.items = new HashSet<>();
        }

        this.items.add(orderItem);

        this.recalculateTotals();
    }

    public void place() {
        this.verifyCanChangeToPlaced();

        this.setPlacedAt(OffsetDateTime.now());
        this.changeStatus(OrderStatus.PLACED);
    }


    public void markAsPaid() {
        this.setPaidAt(OffsetDateTime.now());
        this.changeStatus(OrderStatus.PAID);
    }

    public void changePaymentMethod(PaymentMethod paymentMethod) {
        this.verifyIfChangeable();

        Objects.requireNonNull(paymentMethod);
        this.setPaymentMethod(paymentMethod);
    }

    public void changeBilling(Billing billing) {
        this.verifyIfChangeable();

        Objects.requireNonNull(billing);
        this.setBilling(billing);
    }

    public void changeShipping(Shipping newShipping) {
        this.verifyIfChangeable();

        Objects.requireNonNull(newShipping);

        if (newShipping.expectedDate().isBefore(LocalDate.now())) {
            throw new OrderInvalidShippingDeliveryDateException(this.id());
        }

        this.setShipping(newShipping);
        this.recalculateTotals();
    }

    public void changeItemQuantity(OrderItemId orderItemId, Quantity quantity) {
        this.verifyIfChangeable();

        Objects.requireNonNull(orderItemId);
        Objects.requireNonNull(quantity);

        OrderItem orderItem = this.findOrderItem(orderItemId);
        orderItem.changeQuantity(quantity);

        this.recalculateTotals();
    }

    public boolean isDraft() {
        return OrderStatus.DRAFT.equals(this.status());
    }

    public boolean isPlaced() {
        return OrderStatus.PLACED.equals(this.status());
    }

    public boolean isPaid() {
        return OrderStatus.PAID.equals(this.status());
    }


    public OrderId id() {
        return id;
    }

    public CustomerId customerId() {
        return customerId;
    }

    public Money totalAmount() {
        return totalAmount;
    }

    public Quantity totalItems() {
        return totalItems;
    }

    public OffsetDateTime placedAt() {
        return placedAt;
    }

    public OffsetDateTime paidAt() {
        return paidAt;
    }

    public OffsetDateTime canceledAt() {
        return canceledAt;
    }

    public OffsetDateTime readyAt() {
        return readyAt;
    }

    public Billing billing() {
        return billing;
    }

    public Shipping shipping() {
        return shipping;
    }

    public OrderStatus status() {
        return status;
    }

    public PaymentMethod paymentMethod() {
        return paymentMethod;
    }

    private void recalculateTotals() {
        BigDecimal totalItemsAmount = this.items().stream()
                .map(orderItem -> orderItem.totalAmount().value())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Integer totalItemsQuantity = this.items().stream()
                .map(orderItem -> orderItem.quantity().value())
                .reduce(0, Integer::sum);

        BigDecimal shippingCost;
        if (this.shipping() == null) {
            shippingCost = BigDecimal.ZERO;
        } else {
            shippingCost = this.shipping().cost().value();
        }

        BigDecimal totalAmount = totalItemsAmount.add(shippingCost);

        this.setTotalAmount(new Money(totalAmount));
        this.setTotalItems(new Quantity(totalItemsQuantity));
    }

    public Set<OrderItem> items() {
        return Collections.unmodifiableSet(this.items);
    }

    private void changeStatus(OrderStatus newStatus) {
        Objects.requireNonNull(newStatus);

        if (this.status().canNotChangeTo(newStatus)) {
            throw new OrderStatusCannotBeChangedException(this.id(), this.status(), newStatus);
        }

        this.setStatus(newStatus);
    }

    private void verifyCanChangeToPlaced() {
        if (this.items() == null || this.items().isEmpty()) {
            throw OrderCannotBePlacedExcption.noItems(this.id());
        }

        if (this.shipping() == null) {
            throw OrderCannotBePlacedExcption.noShippingInfo(this.id());
        }

        if (this.billing() == null) {
            throw OrderCannotBePlacedExcption.noBillingInfo(this.id());
        }

        if (this.paymentMethod() == null) {
            throw OrderCannotBePlacedExcption.noPaymentMethod(this.id());
        }
    }

    private OrderItem findOrderItem(OrderItemId orderItemId) {
        Objects.requireNonNull(orderItemId);

        return this.items().stream()
                .filter(orderItem -> orderItem.id().equals(orderItemId))
                .findFirst()
                .orElseThrow(() -> new OrderDoesNotContainOrderItemException(this.id(), orderItemId));
    }

    private void verifyIfChangeable(){
        if (!isDraft()){
            throw new OrderCannotBeEditedException(this.id(), OrderStatus.DRAFT);
        }
    }

    private void setId(OrderId id) {
        this.id = Objects.requireNonNull(id);
    }

    private void setCustomerId(CustomerId customerId) {
        this.customerId = Objects.requireNonNull(customerId);
    }

    private void setTotalAmount(Money totalAmount) {
        this.totalAmount = Objects.requireNonNull(totalAmount);
    }

    private void setTotalItems(Quantity totalItems) {
        this.totalItems = Objects.requireNonNull(totalItems);
    }

    private void setPlacedAt(OffsetDateTime placedAt) {
        this.placedAt = placedAt;
    }

    private void setPaidAt(OffsetDateTime paidAt) {
        this.paidAt = paidAt;
    }

    private void setCanceledAt(OffsetDateTime canceledAt) {
        this.canceledAt = canceledAt;
    }

    private void setReadyAt(OffsetDateTime readyAt) {
        this.readyAt = readyAt;
    }

    private void setBilling(Billing billing) {
        this.billing = billing;
    }

    private void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    private void setStatus(OrderStatus status) {
        this.status = Objects.requireNonNull(status);
    }

    private void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    private void setItems(Set<OrderItem> items) {
        this.items = Objects.requireNonNull(items);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
