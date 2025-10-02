package br.com.guntz.shop.ordering.domain.exception;

import br.com.guntz.shop.ordering.domain.valueobject.id.OrderId;

public class OrderCannotBePlacedException extends DomainException {

    private OrderCannotBePlacedException(String message) {
        super(message);
    }

    public static OrderCannotBePlacedException noItems(OrderId orderId) {
        return new OrderCannotBePlacedException(
                String.format(ErrorMessages.ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_ITEMS, orderId)
        );
    }

    public static OrderCannotBePlacedException noShippingInfo(OrderId orderId) {
        return new OrderCannotBePlacedException(
                String.format(ErrorMessages.ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_SHIPPING_INFO, orderId)
        );
    }

    public static OrderCannotBePlacedException noBillingInfo(OrderId orderId) {
        return new OrderCannotBePlacedException(
                String.format(ErrorMessages.ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_BILLING_INFO, orderId)
        );
    }

    public static OrderCannotBePlacedException noPaymentMethod(OrderId orderId) {
        return new OrderCannotBePlacedException(
                String.format(ErrorMessages.ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_PAYMENT_METHOD, orderId)
        );
    }
}
