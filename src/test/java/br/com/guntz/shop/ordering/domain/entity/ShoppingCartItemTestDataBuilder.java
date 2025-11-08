package br.com.guntz.shop.ordering.domain.entity;

import br.com.guntz.shop.ordering.domain.valueobject.Quantity;

public class ShoppingCartItemTestDataBuilder {

    private ShoppingCartItemTestDataBuilder() {
    }

    public static ShoppingCartItem brandNewShoppingCartItemIphone14() {
        var shoppingCart = ShoppingCartTestDataBuilder.brandNewShoppingCart();
        var product = ProductTestDataBuilder.aProductIphone14().build();
        var quantity = new Quantity(5);

        return ShoppingCartItem.brandNew()
                .shoppingCartId(shoppingCart.id())
                .productId(product.productId())
                .productName(product.productName())
                .price(product.price())
                .quantity(quantity)
                .build();
    }

    public static ShoppingCartItem brandNewShoppingCartItemCanecaPsyTrance() {
        var shoppingCart = ShoppingCartTestDataBuilder.brandNewShoppingCart();
        var product = ProductTestDataBuilder.aProductCanecaPsyTrance().build();
        var quantity = new Quantity(5);

        return ShoppingCartItem.brandNew()
                .shoppingCartId(shoppingCart.id())
                .productId(product.productId())
                .productName(product.productName())
                .price(product.price())
                .quantity(quantity)
                .build();
    }


}
