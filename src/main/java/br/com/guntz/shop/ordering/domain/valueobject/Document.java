package br.com.guntz.shop.ordering.domain.valueobject;

import br.com.guntz.shop.ordering.domain.validator.CPFHelper;
import br.com.guntz.shop.ordering.domain.validator.FieldValidations;

import static br.com.guntz.shop.ordering.domain.exception.ErrorMessages.ERROR_CUSTOMER_DOCUMENT_CPF;
import static br.com.guntz.shop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_DOCUMENT_IS_BLANK;

public record Document(String value) {

    public Document(String value) {
        FieldValidations.requiredNonBlank(value, VALIDATION_ERROR_DOCUMENT_IS_BLANK);
        FieldValidations.requiresValidCPF(value, ERROR_CUSTOMER_DOCUMENT_CPF);

        this.value = value;
    }

    private Boolean isValid(String value) {
        return CPFHelper.isValid(value);
    }

    @Override
    public String toString() {
        return value;
    }

}
