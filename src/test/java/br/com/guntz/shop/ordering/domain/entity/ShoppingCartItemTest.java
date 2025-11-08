package br.com.guntz.shop.ordering.domain.entity;

import br.com.guntz.shop.ordering.domain.exception.ShoppingCartItemIncompatibleProductException;
import br.com.guntz.shop.ordering.domain.valueobject.Quantity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ShoppingCartItemTest {

    @Test
    public void shouldGenerateBrandNewShoppingCartItem() {
        ShoppingCartItem shoppingCartItem = ShoppingCartItemTestDataBuilder.brandNewShoppingCartItemIphone14();
        var product = ProductTestDataBuilder.aProductIphone14().build();
        var quantity = new Quantity(5);

        var correctTotalAmount = product.price().multiply(quantity);

        Assertions.assertThat(shoppingCartItem.totalAmount()).isEqualTo(correctTotalAmount);
    }

    @Test
    public void givenShoppingCartItem_whenChangeQuantityForZero_shouldGenerateException() {
        ShoppingCartItem shoppingCartItem = ShoppingCartItemTestDataBuilder.brandNewShoppingCartItemIphone14();

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> shoppingCartItem.changeQuantity(new Quantity(-1)));
    }

    @Test
    public void givenShoppingCartItem_whenRefreshWithProductIncompatible_shouldGenerateException() {
        ShoppingCartItem shoppingCartItem = ShoppingCartItemTestDataBuilder.brandNewShoppingCartItemIphone14();
        var product = ProductTestDataBuilder.aProductUnavailable().build();

        Assertions.assertThatExceptionOfType(ShoppingCartItemIncompatibleProductException.class)
                .isThrownBy(() -> shoppingCartItem.refresh(product));
    }

    @Test
    public void givenShoppingCartItem_whenTryCompareWithEqualId_shouldReturnTrue() {
        ShoppingCartItem shoppingCartItem = ShoppingCartItemTestDataBuilder.brandNewShoppingCartItemIphone14();
        ShoppingCartItem shoppingCartItem2 = shoppingCartItem;

        Assertions.assertThat(shoppingCartItem).isEqualTo(shoppingCartItem2);
    }
}