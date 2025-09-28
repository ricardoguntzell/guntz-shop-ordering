package br.com.guntz.shop.ordering.domain.factory;

import br.com.guntz.shop.ordering.domain.entity.Order;
import br.com.guntz.shop.ordering.domain.entity.PaymentMethod;
import br.com.guntz.shop.ordering.domain.valueobject.Billing;
import br.com.guntz.shop.ordering.domain.valueobject.Product;
import br.com.guntz.shop.ordering.domain.valueobject.Quantity;
import br.com.guntz.shop.ordering.domain.valueobject.Shipping;
import br.com.guntz.shop.ordering.domain.valueobject.id.CustomerId;

import java.util.Objects;

public class OrderFactory {

    private OrderFactory(){}


    public static Order filled(
            CustomerId customerId,
            Shipping shipping,
            Billing billing,
            PaymentMethod paymentMethod,
            Product product,
            Quantity quantity
    ){
        Objects.requireNonNull(customerId);
        Objects.requireNonNull(shipping);
        Objects.requireNonNull(billing);
        Objects.requireNonNull(paymentMethod);
        Objects.requireNonNull(product);
        Objects.requireNonNull(quantity);

        Order order = Order.draft(customerId);

        order.changeBilling(billing);
        order.changeShipping(shipping);
        order.changePaymentMethod(paymentMethod);
        order.addItem(product, quantity);

        return order;
    }
}
