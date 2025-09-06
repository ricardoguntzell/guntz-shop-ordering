package br.com.guntz.shop.ordering.domain.exception;

public class ErrorMessages {

    public static final String VALIDATION_ERROR_BIRTHDATE_MUST_IN_PAST = "BirthDate must be a past date";

    public static final String VALIDATION_ERROR_FULLNAME_IS_NULL = "FullName cannot be null";

    public static final String VALIDATION_ERROR_FULLNAME_IS_BLANK = "FullName cannot be blank";

    public static final String VALIDATION_ERROR_DOCUMENT_IS_BLANK = "Document cannot be blank";

    public static final String VALIDATION_ERROR_PHONE_IS_BLANK = "Phone cannot be blank";

    public static final String VALIDATION_ERROR_EMAIL_IS_INVALID = "Email is invalid";

    public static final String VALIDATION_ERROR_LOYALTY_POINTS_IS_NEGATIVE = "Loyalty points cannot be negative";

    public static final String VALIDATION_ERROR_LOYALTY_POINTS_IS_ZERO = "Loyalty points cannot be zero";

    public static final String ERROR_CUSTOMER_ARCHIVED = "Customer has already been filed";

    public static final String ERROR_CUSTOMER_DOCUMENT_CPF = "CPF is invalid";

    public static final String ERROR_MONEY = "Money is invalid";

    public static final String ERROR_MONEY_QUANTITY_ZERO = "Money cannot be multiplied by zero";

    public static final String ERROR_QUANTITY = "Quantity is invalid";

    public static final String ERROR_ORDER_STATUS_CANNOT_BE_CHANGED = "Cannot change order %s status from %s to %s";

}
