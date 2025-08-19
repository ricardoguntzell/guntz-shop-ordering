package br.com.guntz.shop.ordering.domain.valueobject;

import br.com.guntz.shop.ordering.domain.exception.CustomerDocumentException;
import br.com.guntz.shop.ordering.domain.validator.CPFHelper;

import java.util.Objects;

import static br.com.guntz.shop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_DOCUMENT_IS_BLANK;

public record Document(String value) {

    public Document(String value) {
        Objects.requireNonNull(value);

        if (value.isBlank()) {
            throw new IllegalArgumentException(VALIDATION_ERROR_DOCUMENT_IS_BLANK);
        }

        if (!isValid(value)) {
            throw new CustomerDocumentException();
        }

        this.value = value;
    }

    private Boolean isValid(String value) {
        return CPFHelper.cpfIsValid(value);
    }

    @Override
    public String toString() {
        return value;
    }

}
