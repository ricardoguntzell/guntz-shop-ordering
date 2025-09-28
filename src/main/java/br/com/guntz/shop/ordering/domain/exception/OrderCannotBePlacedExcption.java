package br.com.guntz.shop.ordering.domain.exception;

import br.com.guntz.shop.ordering.domain.valueobject.id.OrderId;

public class OrderCannotBePlacedExcption extends DomainException {

    private OrderCannotBePlacedExcption(String message) {
        super(message);
    }

    public static OrderCannotBePlacedExcption noItems(OrderId orderId) {
        return new OrderCannotBePlacedExcption(
                String.format(ErrorMessages.ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_ITEMS, orderId)
        );
    }

    public static OrderCannotBePlacedExcption noShippingInfo(OrderId orderId) {
        return new OrderCannotBePlacedExcption(
                String.format(ErrorMessages.ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_SHIPPING_INFO, orderId)
        );
    }

    public static OrderCannotBePlacedExcption noBillingInfo(OrderId orderId) {
        return new OrderCannotBePlacedExcption(
                String.format(ErrorMessages.ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_BILLING_INFO, orderId)
        );
    }

    public static OrderCannotBePlacedExcption noPaymentMethod(OrderId orderId) {
        return new OrderCannotBePlacedExcption(
                String.format(ErrorMessages.ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_PAYMENT_METHOD, orderId)
        );
    }
}
