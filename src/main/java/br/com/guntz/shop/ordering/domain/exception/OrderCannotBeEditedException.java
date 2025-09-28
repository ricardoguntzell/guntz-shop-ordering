package br.com.guntz.shop.ordering.domain.exception;

import br.com.guntz.shop.ordering.domain.entity.OrderStatus;
import br.com.guntz.shop.ordering.domain.valueobject.id.OrderId;

import static br.com.guntz.shop.ordering.domain.exception.ErrorMessages.ERROR_ORDER_CANNOT_BE_EDITED;

public class OrderCannotBeEditedException extends DomainException {

    public OrderCannotBeEditedException(OrderId orderId, OrderStatus draft) {
        super(String.format(ERROR_ORDER_CANNOT_BE_EDITED, orderId, draft));

    }
}
