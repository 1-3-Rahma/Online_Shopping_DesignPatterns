package com.shopping.payment;

import com.shopping.models.Payment;

import java.util.UUID;

public class PayPal implements PaymentMethod {
    private final String email;

    public PayPal(String email) { this.email = email; }

    @Override
    public Payment pay(double amount) {
        boolean success = amount > 0;
        return new Payment(name(), amount, success, UUID.randomUUID().toString());
    }

    @Override public String name() { return "PayPal(" + email + ")"; }
}
