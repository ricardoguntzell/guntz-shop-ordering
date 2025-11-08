package br.com.guntz.shop.ordering.domain.entity;

import br.com.guntz.shop.ordering.domain.valueobject.*;
import br.com.guntz.shop.ordering.domain.valueobject.id.CustomerId;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class ShoppingCartTestDataBuilder {

    private ShoppingCartTestDataBuilder() {
    }

    public static ShoppingCart brandNewShoppingCart() {
        var customer = CustomerTestDataBuilder.existingAnonymizedCustomer().build();

        return ShoppingCart.startShopping(customer.id());
    }

    public static ShoppingCart brandNewShoppingCartWithItems() {
        var shoppingCart = ShoppingCartTestDataBuilder.brandNewShoppingCart();
        shoppingCart.addItem(ProductTestDataBuilder.aProductCanecaPsyTrance().build(), new Quantity(1));

        return shoppingCart;
    }
}
