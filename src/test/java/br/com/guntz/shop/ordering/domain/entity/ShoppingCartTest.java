package br.com.guntz.shop.ordering.domain.entity;

import br.com.guntz.shop.ordering.domain.exception.ProductOutOfStockException;
import br.com.guntz.shop.ordering.domain.exception.ShoppingCartDoesNotContainItemException;
import br.com.guntz.shop.ordering.domain.exception.ShoppingCartItemIncompatibleProductException;
import br.com.guntz.shop.ordering.domain.valueobject.Money;
import br.com.guntz.shop.ordering.domain.valueobject.Product;
import br.com.guntz.shop.ordering.domain.valueobject.Quantity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ShoppingCartTest {

    @Test
    public void shouldCorrectDataForShoppingCartBrandNew() {
        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.brandNewShoppingCart();

        Assertions.assertWith(shoppingCart,
                sc -> Assertions.assertThat(sc.totalAmount()).isEqualTo(Money.ZERO),
                sc -> Assertions.assertThat(sc.totalItems()).isEqualTo(Quantity.ZERO),
                sc -> Assertions.assertThat(sc.isEmpty()).isEqualTo(Boolean.TRUE)
        );
    }

    @Test
    public void givenShoppingCart_whenTryInsertItemWithProductNoQuantityInStock_shouldGenerateException() {
        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.brandNewShoppingCartWithItems();
        var productUnavailable = ProductTestDataBuilder.aProductUnavailable().build();

        Assertions.assertThatExceptionOfType(ProductOutOfStockException.class)
                .isThrownBy(() -> shoppingCart
                        .addItem(productUnavailable, new Quantity(10)));
    }

    @Test
    public void shouldCorrectDataForItemInsertedTwoTimes() {
        Product product = ProductTestDataBuilder.aProductCanecaPsyTrance().build();
        Quantity quantity = new Quantity(1);
        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.brandNewShoppingCart();

        var totalItemsExpected = quantity.add(quantity);
        var totalAmoutExpected = product.price().multiply(totalItemsExpected);

        shoppingCart.addItem(product, quantity);
        shoppingCart.addItem(product, quantity);

        Assertions.assertWith(shoppingCart,
                sc -> Assertions.assertThat(sc.totalAmount()).isEqualTo(totalAmoutExpected),
                sc -> Assertions.assertThat(sc.totalItems()).isEqualTo(totalItemsExpected)
        );
    }

    @Test
    public void shouldCorrectDataForItemInsertedWithTwoProductsDistincts() {
        Product product = ProductTestDataBuilder.aProductCanecaPsyTrance().build();
        Product product2 = ProductTestDataBuilder.aProductIphone14().build();
        Quantity quantity = new Quantity(1);
        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.brandNewShoppingCart();

        var totalItemsExpected = quantity.add(quantity);
        var totalAmoutExpected = product.price().multiply(quantity);
        Money finalTotalAmoutExpected = totalAmoutExpected.add(product2.price().multiply(quantity));

        shoppingCart.addItem(product, quantity);
        shoppingCart.addItem(product2, quantity);

        Assertions.assertWith(shoppingCart,
                sc -> Assertions.assertThat(sc.totalAmount()).isEqualTo(finalTotalAmoutExpected),
                sc -> Assertions.assertThat(sc.totalItems()).isEqualTo(totalItemsExpected)
        );
    }

    @Test
    public void givenShoppingCart_whenTryRemoveItemNoExist_shouldGenerateException() {
        var shoppingCart = ShoppingCartTestDataBuilder.brandNewShoppingCartWithItems();
        var shoppingCartItem = ShoppingCartItemTestDataBuilder.brandNewShoppingCartItemIphone14();

        Assertions.assertThatExceptionOfType(ShoppingCartDoesNotContainItemException.class)
                .isThrownBy(() -> shoppingCart.removeItem(shoppingCartItem.id()));
    }

    @Test
    public void shouldCorrectDataForShoppingCartIsEmpty() {
        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.brandNewShoppingCartWithItems();
        shoppingCart.empty();

        Assertions.assertWith(shoppingCart,
                sc -> Assertions.assertThat(sc.totalAmount()).isEqualTo(Money.ZERO),
                sc -> Assertions.assertThat(sc.totalItems()).isEqualTo(Quantity.ZERO),
                sc -> Assertions.assertThat(sc.isEmpty()).isEqualTo(Boolean.TRUE)
        );
    }

    @Test
    public void givenShoppingCart_whenUpdateItemWithProductIncompatible_shouldGenerateException() {
        ShoppingCartItem shoppingCartItem = ShoppingCartItemTestDataBuilder.brandNewShoppingCartItemIphone14();
        var product = ProductTestDataBuilder.aProductUnavailable().build();

        Assertions.assertThatExceptionOfType(ShoppingCartItemIncompatibleProductException.class)
                .isThrownBy(() -> shoppingCartItem.refresh(product));
    }

    @Test
    public void givenShoppingCart_whenTryCompareWithEqualId_shouldReturnTrue() {
        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.brandNewShoppingCart();
        ShoppingCart shoppingCart2 = shoppingCart;

        Assertions.assertThat(shoppingCart).isEqualTo(shoppingCart2);
    }

}