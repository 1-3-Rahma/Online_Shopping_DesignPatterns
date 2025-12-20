package com.shopping.orders;

import com.shopping.models.Order;

public abstract class OrderValidator {
    private OrderValidator next;
    public OrderValidator linkWith(OrderValidator next) { this.next = next; return next; }
    public boolean validate(Order order) { return check(order) && (next == null || next.validate(order)); }
    protected abstract boolean check(Order order);
}
