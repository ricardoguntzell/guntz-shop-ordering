package br.com.guntz.shop.ordering.domain.exception;

import br.com.guntz.shop.ordering.domain.valueobject.id.ProductId;

public class ProductOutOfStockException extends DomainException {

    public ProductOutOfStockException(ProductId productId) {
        super(String.format(ErrorMessages.ERROR_PRODUCT_IS_OUT_OF_STOCK, productId));
    }
}
