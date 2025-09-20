package br.com.guntz.shop.ordering.domain.entity;

import br.com.guntz.shop.ordering.domain.valueobject.Money;
import br.com.guntz.shop.ordering.domain.valueobject.Product;
import br.com.guntz.shop.ordering.domain.valueobject.ProductName;
import br.com.guntz.shop.ordering.domain.valueobject.id.ProductId;

public class ProductTestDataBuilder {

    private ProductTestDataBuilder() {
    }

    public static Product.ProductBuilder aProduct() {
        return Product.builder()
                .productId(new ProductId())
                .productName(new ProductName("PS5"))
                .price(new Money("4000"))
                .inStock(true);
    }

    public static Product.ProductBuilder aProductUnavailable() {
        return Product.builder()
                .productId(new ProductId())
                .productName(new ProductName("Iphone 14"))
                .price(new Money("4500"))
                .inStock(false);
    }

    public static Product.ProductBuilder aProductIphone14() {
        return Product.builder()
                .productId(new ProductId())
                .productName(new ProductName("Iphone 14"))
                .price(new Money("4500"))
                .inStock(true);
    }

    public static Product.ProductBuilder aProductCanecaPsyTrance() {
        return Product.builder()
                .productId(new ProductId())
                .productName(new ProductName("Caneca PsyTrance"))
                .price(new Money("80"))
                .inStock(true);
    }

}
