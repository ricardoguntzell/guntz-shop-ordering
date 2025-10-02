package br.com.guntz.shop.ordering.domain.exception;

import br.com.guntz.shop.ordering.domain.entity.OrderStatus;
import br.com.guntz.shop.ordering.domain.valueobject.id.OrderId;

import static br.com.guntz.shop.ordering.domain.exception.ErrorMessages.ERROR_ORDER_STATUS_CANNOT_BE_CHANGED;

public class OrderStatusCannotBeChangedException extends DomainException {

    public OrderStatusCannotBeChangedException(OrderId id, OrderStatus status, OrderStatus newStatus) {
        super(String.format(ERROR_ORDER_STATUS_CANNOT_BE_CHANGED, id, status, newStatus));
    }
}
