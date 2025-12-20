package com.shopping.payment;

import com.shopping.models.Payment;

public interface PaymentMethod {
    Payment pay(double amount);
    String name();
}
