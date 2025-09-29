package br.com.guntz.shop.ordering.domain.entity;


import br.com.guntz.shop.ordering.domain.valueobject.Product;
import br.com.guntz.shop.ordering.domain.valueobject.Quantity;
import br.com.guntz.shop.ordering.domain.valueobject.id.OrderId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderItemTest {

    @Test
    public void shouldGenerateBrandNewOrderItem() {
        Product product = ProductTestDataBuilder.aProduct().build();
        Quantity quantity = new Quantity(1);
        OrderId orderId = new OrderId();

        OrderItem orderItem = OrderItem.brandNew()
                .orderId(orderId)
                .product(product)
                .quantity(quantity)
                .build();

        Assertions.assertWith(orderItem,
                o -> Assertions.assertThat(o.orderItemId()).isNotNull(),
                o -> Assertions.assertThat(o.productId()).isEqualTo(product.productId()),
                o -> Assertions.assertThat(o.orderId()).isEqualTo(orderId),
                o -> Assertions.assertThat(o.productName()).isEqualTo(product.productName()),
                o -> Assertions.assertThat(o.price()).isEqualTo(product.price()),
                o -> Assertions.assertThat(o.quantity()).isEqualTo(quantity)
        );
    }


}