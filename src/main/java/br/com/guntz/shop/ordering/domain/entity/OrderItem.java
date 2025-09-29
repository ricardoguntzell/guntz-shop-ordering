package br.com.guntz.shop.ordering.domain.entity;

import br.com.guntz.shop.ordering.domain.valueobject.Money;
import br.com.guntz.shop.ordering.domain.valueobject.Product;
import br.com.guntz.shop.ordering.domain.valueobject.ProductName;
import br.com.guntz.shop.ordering.domain.valueobject.Quantity;
import br.com.guntz.shop.ordering.domain.valueobject.id.OrderId;
import br.com.guntz.shop.ordering.domain.valueobject.id.OrderItemId;
import br.com.guntz.shop.ordering.domain.valueobject.id.ProductId;
import lombok.Builder;

import java.util.Objects;

public class OrderItem {

    private OrderItemId orderItemId;
    private OrderId orderId;

    private ProductId productId;
    private ProductName productName;

    private Money price;
    private Quantity quantity;

    private Money totalAmount;

    @Builder(builderClassName = "ExistingOrderItemBuilder", builderMethodName = "existing")
    public OrderItem(OrderItemId orderItemId, OrderId orderId, ProductId productId, ProductName productName, Money price, Quantity quantity, Money totalAmount) {
        this.setOrderItemId(orderItemId);
        this.setOrderId(orderId);
        this.setProductId(productId);
        this.setProductName(productName);
        this.setPrice(price);
        this.setQuantity(quantity);
        this.setTotalAmount(totalAmount);
    }

    @Builder(builderClassName = "BrandNewOrderItemBuilder", builderMethodName = "brandNew")
    private static OrderItem createBrandNew(OrderId orderId, Product product, Quantity quantity) {
        Objects.requireNonNull(orderId);
        Objects.requireNonNull(product);
        Objects.requireNonNull(quantity);

        OrderItem orderItem = new OrderItem(
                new OrderItemId(),
                orderId,
                product.productId(),
                product.productName(),
                product.price(),
                quantity,
                Money.ZERO
        );

        orderItem.recalculateTotals();

        return orderItem;
    }

    void changeQuantity(Quantity quantity) {
        Objects.requireNonNull(quantity);
        this.setQuantity(quantity);
        this.recalculateTotals();
    }

    public OrderItemId orderItemId() {
        return orderItemId;
    }

    public OrderId orderId() {
        return orderId;
    }

    public ProductId productId() {
        return productId;
    }

    public ProductName productName() {
        return productName;
    }

    public Money price() {
        return price;
    }

    public Quantity quantity() {
        return quantity;
    }

    public Money totalAmount() {
        return totalAmount;
    }

    private void recalculateTotals() {
        this.setTotalAmount(this.price().multiply(this.quantity()));
    }

    private void setOrderItemId(OrderItemId id) {
        this.orderItemId = Objects.requireNonNull(id);
    }

    private void setOrderId(OrderId orderId) {
        this.orderId = Objects.requireNonNull(orderId);
    }

    private void setProductId(ProductId productId) {
        this.productId = Objects.requireNonNull(productId);
    }

    private void setProductName(ProductName productName) {
        this.productName = Objects.requireNonNull(productName);
    }

    private void setPrice(Money price) {
        this.price = Objects.requireNonNull(price);
    }

    private void setQuantity(Quantity quantity) {
        this.quantity = Objects.requireNonNull(quantity);
    }

    private void setTotalAmount(Money totalAmount) {
        this.totalAmount = Objects.requireNonNull(totalAmount);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(orderItemId, orderItem.orderItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(orderItemId);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
               "orderItemId=" + orderItemId +
               ", orderId=" + orderId +
               ", productId=" + productId +
               ", productName=" + productName +
               ", price=" + price +
               ", quantity=" + quantity +
               ", totalAmount=" + totalAmount +
               '}';
    }
}
