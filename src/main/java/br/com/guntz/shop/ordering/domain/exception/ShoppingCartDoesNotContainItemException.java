package br.com.guntz.shop.ordering.domain.exception;

import br.com.guntz.shop.ordering.domain.valueobject.id.ShoppingCartId;
import br.com.guntz.shop.ordering.domain.valueobject.id.ShoppingCartItemId;

import static br.com.guntz.shop.ordering.domain.exception.ErrorMessages.ERROR_SHOPPING_CART_DOES_NOT_CONTAIN_ITEM;

public class ShoppingCartDoesNotContainItemException extends DomainException {
    public ShoppingCartDoesNotContainItemException(ShoppingCartId id,  ShoppingCartItemId shoppingCartItemId) {
        super(String.format(ERROR_SHOPPING_CART_DOES_NOT_CONTAIN_ITEM, id, shoppingCartItemId));
    }
}
