package br.com.guntz.shop.ordering.domain.entity;

import br.com.guntz.shop.ordering.domain.valueobject.Money;
import br.com.guntz.shop.ordering.domain.valueobject.Quantity;
import br.com.guntz.shop.ordering.domain.valueobject.id.CustomerId;
import br.com.guntz.shop.ordering.domain.valueobject.id.ShoppingCartId;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ShoppingCart {

    private ShoppingCartId id;
    private CustomerId customerId;

    private Money totalAmount;
    private Quantity totalItems;
    private OffsetDateTime createdAt;

    private Set<ShoppingCartItem> items;

    @Builder(builderClassName = "ExistingShoppingCartBuilder", builderMethodName = "existing")
    public ShoppingCart(ShoppingCartId id, CustomerId customerId, Money totalAmount, Quantity totalItems,
                        OffsetDateTime createdAt, Set<ShoppingCartItem> items) {
        this.setId(id);
        this.setCustomerId(customerId);
        this.setTotalAmount(totalAmount);
        this.setTotalItems(totalItems);
        this.setCreatedAt(createdAt);
        this.setItems(items);
    }

    public static ShoppingCart startShopping(CustomerId customerId) {
        return new ShoppingCart(
                new ShoppingCartId(),
                customerId,
                Money.ZERO,
                Quantity.ZERO,
                OffsetDateTime.now(),
                new HashSet<>()
        );
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
