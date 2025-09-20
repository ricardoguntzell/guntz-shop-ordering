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

    public static final String ERROR_ORDER_DELIVERY_DATE_CANNOT_BE_IN_THE_PAST
            = "Order %s expected delivery date cannot be in the past";

    public static final String ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_ITEMS
            = "Order %s cannot be placed, it has no items";

    public static final String ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_BILLING_INFO
            = "Order %s cannot be placed, it has no billing info";

    public static final String ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_SHIPPING_INFO
            = "Order %s cannot be placed, it has no shipping info";

    public static final String ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_PAYMENT_METHOD
            = "Order %s cannot be placed, it has no payment method";

    public static final String ERROR_ORDER_CANNOT_BE_PLACED_HAS_INVALID_SHIPPING_COST
            = "Order %s cannot be placed, it has invalid shipping cost";

    public static final String ERROR_ORDER_CANNOT_BE_PLACED_HAS_INVALID_DELIVERY_DATE
            = "Order %s cannot be placed, it has invalid delivery date";

    public static final String ERROR_ORDER_DOES_NOT_CONTAIN_ITEM
            = "Order %s does not contain item %s";

    public static final String ERROR_PRODUCT_IS_OUT_OF_STOCK
            = "Product %s is out of stock";
}
