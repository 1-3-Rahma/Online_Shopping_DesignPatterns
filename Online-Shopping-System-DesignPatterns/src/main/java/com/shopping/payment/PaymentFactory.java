package com.shopping.payment;

public class PaymentFactory {
    public enum PaymentType { CREDIT_CARD, PAYPAL, LEGACY_GATEWAY }

    public PaymentMethod create(PaymentType type, String credential) {
        return switch (type) {
            case CREDIT_CARD -> new CreditCard(credential);
            case PAYPAL -> new PayPal(credential);
            case LEGACY_GATEWAY -> new PaymentAdapter(new LegacyGateway(credential));
        };
    }
}
