package com.shopping.controllers;

import com.shopping.models.Payment;
import com.shopping.payment.PaymentFactory;
import com.shopping.payment.PaymentMethod;

public class PaymentController {
    private final PaymentFactory factory = new PaymentFactory();

    public Payment pay(PaymentFactory.PaymentType type, String credential, double amount) {
        PaymentMethod method = factory.create(type, credential);
        return method.pay(amount);
    }
}
