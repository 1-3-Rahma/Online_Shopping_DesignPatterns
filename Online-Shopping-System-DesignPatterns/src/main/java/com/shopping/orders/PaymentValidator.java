package com.shopping.orders;

import com.shopping.models.Order;

public class PaymentValidator extends OrderValidator {
    @Override protected boolean check(Order order) {
        return order.getPayment() != null && order.getPayment().isSuccess();
    }
}
