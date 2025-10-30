package br.com.guntz.shop.ordering.domain.entity;

import br.com.guntz.shop.ordering.domain.valueobject.Money;
import br.com.guntz.shop.ordering.domain.valueobject.Product;
import br.com.guntz.shop.ordering.domain.valueobject.ProductName;
import br.com.guntz.shop.ordering.domain.valueobject.Quantity;
import br.com.guntz.shop.ordering.domain.valueobject.id.ProductId;
import br.com.guntz.shop.ordering.domain.valueobject.id.ShoppingCartId;
import br.com.guntz.shop.ordering.domain.valueobject.id.ShoppingCartItemId;
import lombok.Builder;

import java.util.Objects;

public class ShoppingCartItem {

    private ShoppingCartItemId id;
    private ShoppingCartId shoppingCartId;

    private ProductId productId;
    private ProductName productName;
    private Money price;
    private Quantity quantity;
    private Money totalAmount;
    private Boolean available;

    @Builder(builderClassName = "ExistingShoppingCartItemBuilder", builderMethodName = "existing")
    public ShoppingCartItem(ShoppingCartItemId id, ShoppingCartId shoppingCartId, ProductId productId,
                            ProductName productName, Money price, Quantity quantity, Money totalAmount,
                            Boolean available) {
        this.setId(id);
        this.setShoppingCartId(shoppingCartId);
        this.setProductId(productId);
        this.setProductName(productName);
        this.setPrice(price);
        this.setQuantity(quantity);
        this.setTotalAmount(totalAmount);
        this.setAvailable(available);
    }

    @Builder(builderClassName = "BrandNewShoppingCartItemBuilder", builderMethodName = "brandNew")
    public ShoppingCartItem createBrandNew(ShoppingCartId shoppingCartId, ProductId productId,
                                           ProductName productName, Money price, Quantity quantity) {
        Objects.requireNonNull(shoppingCartId);
        Objects.requireNonNull(productId);
        Objects.requireNonNull(productName);
        Objects.requireNonNull(price);
        Objects.requireNonNull(quantity);

        this.recalculateTotals();
        this.setAvailable(Boolean.TRUE);

        return new ShoppingCartItem(
                new ShoppingCartItemId(),
                shoppingCartId,
                productId,
                productName,
                price,
                quantity,
                this.totalAmount(),
                this.isAvailable()
        );
    }

    public ShoppingCartItemId id() {
        return id;
    }

    public ShoppingCartId shoppingCartId() {
        return shoppingCartId;
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

    public Boolean isAvailable() {
        return available;
    }

    private void recalculateTotals() {
        this.setTotalAmount(this.price().multiply(this.quantity()));
    }

    private void setId(ShoppingCartItemId id) {
        this.id = Objects.requireNonNull(id);
    }

    private void setShoppingCartId(ShoppingCartId shoppingCartId) {
        this.shoppingCartId = Objects.requireNonNull(shoppingCartId);
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

    private void setAvailable(Boolean available) {
        this.available = Objects.requireNonNull(available);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCartItem that = (ShoppingCartItem) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
