package com.shopping.payment;

import com.shopping.models.Payment;

public class PaymentAdapter implements PaymentMethod {
    private final LegacyGateway legacy;

    public PaymentAdapter(LegacyGateway legacy) { this.legacy = legacy; }

    @Override
    public Payment pay(double amount) {
        String tx = legacy.makePayment(amount);
        return new Payment(name(), amount, tx != null, tx);
    }

    @Override public String name() { return "LegacyGateway(token=" + legacy.getToken() + ")"; }
}
