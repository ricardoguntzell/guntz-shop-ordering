package br.com.guntz.shop.ordering.domain.exception;

public class CustomerDocumentException extends DomainException {

    public CustomerDocumentException(String message) {
        super(message);
    }

    public CustomerDocumentException(String message, Throwable cause) {
        super(message, cause);
    }
}
