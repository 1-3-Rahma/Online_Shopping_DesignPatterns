package com.shopping.payment;

import com.shopping.models.Payment;

import java.util.UUID;

public class CreditCard implements PaymentMethod {
    private final String maskedCard;

    public CreditCard(String maskedCard) {
        this.maskedCard = maskedCard;
    }

    @Override
    public Payment pay(double amount) {
        boolean success = amount > 0;
        return new Payment(name(), amount, success, UUID.randomUUID().toString());
    }

    @Override
    public String name() {
        return "CreditCard(" + maskedCard + ")";
    }
}
