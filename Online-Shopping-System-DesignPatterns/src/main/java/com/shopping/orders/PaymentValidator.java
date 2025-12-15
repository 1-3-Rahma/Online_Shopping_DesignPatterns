package com.shopping.orders;

import com.shopping.models.Order;

public class PaymentValidator extends OrderValidator {
    @Override
    protected boolean check(Order order) {
        if (order.getPayment() == null || !order.getPayment().isSuccess()) {
            System.out.println("PaymentValidator: Payment failed / missing");
            return false;
        }
        return true;
    }
}
