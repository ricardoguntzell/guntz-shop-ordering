package br.com.guntz.shop.ordering.domain.valueobject;

import br.com.guntz.shop.ordering.domain.exception.ProductOutOfStockException;
import br.com.guntz.shop.ordering.domain.valueobject.id.ProductId;
import lombok.Builder;

import java.util.Objects;

@Builder
public record Product(ProductId productId,
                      ProductName productName,
                      Money price,
                      Boolean inStock
) {

    public Product {
        Objects.requireNonNull(productId);
        Objects.requireNonNull(productName);
        Objects.requireNonNull(price);
        Objects.requireNonNull(inStock);
    }

    public void checkOutOfStock(){
        if (isOutOfStock()){
            throw new ProductOutOfStockException(this.productId());
        }
    }

    private boolean isOutOfStock() {
        return !inStock();
    }

}
