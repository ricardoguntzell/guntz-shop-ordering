package br.com.guntz.shop.ordering.domain.entity;

import br.com.guntz.shop.ordering.domain.valueobject.*;
import br.com.guntz.shop.ordering.domain.valueobject.id.CustomerId;

import java.time.LocalDate;

public class OrderTestDataBuilder {

    private CustomerId customerId = new CustomerId();

    private PaymentMethod paymentMethod = PaymentMethod.GATEWAY_BALANCE;

    private Shipping shipping = aShipping();

    private Billing billing = aBilling();

    private boolean withItems = true;

    private OrderStatus status = OrderStatus.DRAFT;

    private OrderTestDataBuilder() {
    }

    public static OrderTestDataBuilder anOrder() {
        return new OrderTestDataBuilder();
    }

    public Order build() {
        Order order = Order.draft(customerId);
        order.changeShipping(shipping);
        order.changeBilling(billing);
        order.changePaymentMethod(paymentMethod);

        if (withItems) {
            order.addItem(
                    ProductTestDataBuilder.aProduct().build(),
                    new Quantity(1)
            );

            order.addItem(
                    ProductTestDataBuilder.aProductIphone14().build(),
                    new Quantity(3)
            );
        }

        switch (this.status) {
            case DRAFT -> {
            }

            case PLACED -> {
                order.place();
            }

            case PAID -> {
                order.place();
                order.markAsPaid();
            }

            case READY -> {
            }

            case CANCELED -> {
            }
        }

        return order;
    }

    public static Shipping aShipping() {
        return Shipping.builder()
                .cost(new Money("10.00"))
                .address(anAddress())
                .expectedDate(LocalDate.now().plusWeeks(1))
                .recipient(
                        Recipient.builder()
                                .document(new Document("806.571.170-72"))
                                .phone(new Phone("11 12345-8888"))
                                .fullName(new FullName("Anonymous", "Anonymous"))
                                .build())
                .build();
    }

    public static Shipping aShippingAlt() {
        return Shipping.builder()
                .cost(new Money("20.00"))
                .expectedDate(LocalDate.now().plusWeeks(2))
                .address(anAddressAlt())
                .recipient(
                        Recipient.builder()
                                .document(new Document("560.698.230-79"))
                                .phone(new Phone("11 12345-9797"))
                                .fullName(new FullName("Carlos", "Vilagran"))
                                .build())
                .build();
    }

    public static Billing aBilling() {
        return Billing.builder()
                .address(anAddress())
                .document(new Document("806.571.170-72"))
                .phone(new Phone("11 12345-8888"))
                .email(new Email("anonymous.admin@shop.com.br"))
                .fullName(new FullName("Anonymous", "Anonymous"))
                .build();
    }

    public static Address anAddress() {
        return Address.builder()
                .street("Anonymous street")
                .number("000")
                .neighborhood("Anonymous neighborhood")
                .city("Anonymous city")
                .state("Anonymous state")
                .zipCode(new ZipCode("12345000"))
                .complement("Anonymous complement")
                .build();
    }

    public static Address anAddressAlt() {
        return Address.builder()
                .street("Rua do Psy")
                .number("100")
                .neighborhood("Vila Mariana")
                .city("São Paulo")
                .state("São Paulo")
                .zipCode(new ZipCode("04105002"))
                .complement("AP 99")
                .build();
    }

    public OrderTestDataBuilder customerId(CustomerId customerId) {
        this.customerId = customerId;
        return this;
    }

    public OrderTestDataBuilder paymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public OrderTestDataBuilder shipping(Shipping shipping) {
        this.shipping = shipping;
        return this;
    }

    public OrderTestDataBuilder billing(Billing billing) {
        this.billing = billing;
        return this;
    }

    public OrderTestDataBuilder withItems(boolean withItems) {
        this.withItems = withItems;
        return this;
    }

    public OrderTestDataBuilder status(OrderStatus status) {
        this.status = status;
        return this;
    }

}
