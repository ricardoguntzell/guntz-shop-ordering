package br.com.guntz.shop.ordering.domain.valueobject;

import br.com.guntz.shop.ordering.domain.validator.FieldValidations;

import static br.com.guntz.shop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_FULLNAME_IS_BLANK;

public record FullName(String firstName, String lastName) {

    public FullName(String firstName, String lastName) {
        FieldValidations.requiredNonBlank(firstName, VALIDATION_ERROR_FULLNAME_IS_BLANK);
        FieldValidations.requiredNonBlank(lastName, VALIDATION_ERROR_FULLNAME_IS_BLANK);
        
        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
    }

    @Override
    public String toString() {
        return firstName
                .concat(" ")
                .concat(lastName);
    }
}
