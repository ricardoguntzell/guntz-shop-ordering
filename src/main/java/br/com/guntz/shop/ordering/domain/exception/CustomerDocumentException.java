package br.com.guntz.shop.ordering.domain.exception;

import static br.com.guntz.shop.ordering.domain.exception.ErrorMessages.ERROR_CUSTOMER_DOCUMENT;

public class CustomerDocumentException extends DomainException {

    public CustomerDocumentException() {
        super(ERROR_CUSTOMER_DOCUMENT);
    }

    public CustomerDocumentException(Throwable cause) {
        super(ERROR_CUSTOMER_DOCUMENT, cause);
    }
}
