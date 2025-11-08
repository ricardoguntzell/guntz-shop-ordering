package br.com.guntz.shop.ordering.domain.entity;

import br.com.guntz.shop.ordering.domain.exception.ShoppingCartDoesNotContainItemException;
import br.com.guntz.shop.ordering.domain.exception.ShoppingCartDoesNotContainProductException;
import br.com.guntz.shop.ordering.domain.valueobject.Money;
import br.com.guntz.shop.ordering.domain.valueobject.Product;
import br.com.guntz.shop.ordering.domain.valueobject.Quantity;
import br.com.guntz.shop.ordering.domain.valueobject.id.CustomerId;
import br.com.guntz.shop.ordering.domain.valueobject.id.ProductId;
import br.com.guntz.shop.ordering.domain.valueobject.id.ShoppingCartId;
import br.com.guntz.shop.ordering.domain.valueobject.id.ShoppingCartItemId;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;

public class ShoppingCart {

    private ShoppingCartId id;
    private CustomerId customerId;

    private Money totalAmount;
    private Quantity totalItems;
    private OffsetDateTime createdAt;

    private Set<ShoppingCartItem> items;

    @Builder(builderClassName = "ExistingShoppingCartBuilder", builderMethodName = "existing")
    public ShoppingCart(ShoppingCartId id, CustomerId customerId, Money totalAmount, Quantity totalItems, OffsetDateTime createdAt, Set<ShoppingCartItem> items) {
        this.setId(id);
        this.setCustomerId(customerId);
        this.setTotalAmount(totalAmount);
        this.setTotalItems(totalItems);
        this.setCreatedAt(createdAt);
        this.setItems(items);
    }

    public static ShoppingCart startShopping(CustomerId customerId) {
        return new ShoppingCart(new ShoppingCartId(), customerId, Money.ZERO, Quantity.ZERO, OffsetDateTime.now(), new HashSet<>());
    }

    public void empty() {
        this.items.clear();
        this.setTotalAmount(Money.ZERO);
        this.setTotalItems(Quantity.ZERO);
        this.recalculateTotals();
    }

    public void addItem(Product product, Quantity quantity) {
        Objects.requireNonNull(product);
        Objects.requireNonNull(quantity);

        product.checkOutOfStock();

        ShoppingCartItem shoppingCartItem = ShoppingCartItem.brandNew().shoppingCartId(this.id).productId(product.productId()).productName(product.productName()).price(product.price()).quantity(quantity).build();

        this.searchItemByProduct(product.productId())
                .ifPresentOrElse(
                        item -> updateItem(item, product, quantity),
                        () -> insertItem(shoppingCartItem));

        this.recalculateTotals();
    }

    public void removeItem(ShoppingCartItemId shoppingCartItemId) {
        var item = findItem(shoppingCartItemId);
        this.items.remove(item);
        this.recalculateTotals();
    }

    public void refreshItem(Product product) {
        this.findItem(product.productId()).refresh(product);

        this.recalculateTotals();
    }

    public void changeItemQuantity(ShoppingCartItemId shoppingCartItemId, Quantity quantity) {
        this.findItem(shoppingCartItemId).changeQuantity(quantity);

        this.recalculateTotals();
    }

    public ShoppingCartItem findItem(ShoppingCartItemId shoppingCartItemId) {
        return this.items.stream()
                .filter(
                        item -> item.id().equals(shoppingCartItemId))
                .findFirst()
                .orElseThrow(() -> new ShoppingCartDoesNotContainItemException(this.id, shoppingCartItemId));
    }

    public ShoppingCartItem findItem(ProductId productId) {
        return this.items.stream()
                .filter(
                        item -> item.productId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ShoppingCartDoesNotContainProductException(this.id, productId));
    }

    public void recalculateTotals() {
        BigDecimal totalItemsAmount = this.items().stream().map(i -> i.totalAmount().value()).reduce(BigDecimal.ZERO, BigDecimal::add);

        Integer totalItemsQuantity = this.items().stream().map(i -> i.quantity().value()).reduce(0, Integer::sum);

        this.setTotalAmount(new Money(totalItemsAmount));
        this.setTotalItems(new Quantity(totalItemsQuantity));
    }

    public Boolean isEmpty() {
        return this.items().isEmpty();
    }

    public Boolean containsUnavailableItems() {
        return this.items.stream()
                .anyMatch(ShoppingCartItem::isAvailable);
    }

    public ShoppingCartId id() {
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

    public OffsetDateTime createdAt() {
        return createdAt;
    }

    public Set<ShoppingCartItem> items() {
        return Collections.unmodifiableSet(this.items);
    }

    private void setId(ShoppingCartId id) {
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

    private void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = Objects.requireNonNull(createdAt);
    }

    private void setItems(Set<ShoppingCartItem> items) {
        this.items = Objects.requireNonNull(items);
    }

    private void insertItem(ShoppingCartItem shoppingCartItem) {
        this.items.add(shoppingCartItem);
    }

    private void updateItem(ShoppingCartItem item, Product product, Quantity quantity) {
        item.refresh(product);
        item.changeQuantity(item.quantity().add(quantity));
    }

    private Optional<ShoppingCartItem> searchItemByProduct(ProductId productId) {
        return this.items.stream()
                .filter(item -> item.productId().equals(productId)).findFirst();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCart that = (ShoppingCart) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
