package com.shopping.controllers;

import com.shopping.models.CartItem;
import com.shopping.models.Order;
import com.shopping.models.Payment;
import com.shopping.models.User;
import com.shopping.orders.OrderBuilder;
import com.shopping.orders.OrderValidator;
import com.shopping.orders.PaymentValidator;
import com.shopping.orders.StockValidator;

import java.util.ArrayList;
import java.util.List;

public class OrderController {
    private final List<Order> orders = new ArrayList<>();

    public Order buildOrder(User user, List<CartItem> items, String address, Payment payment) {
        return new OrderBuilder().user(user).items(items).address(address).payment(payment).build();
    }

    public boolean validate(Order order) {
        OrderValidator validator = new StockValidator();
        validator.linkWith(new PaymentValidator());
        return validator.validate(order);
    }

    public void applyStock(Order order) {
        order.getItems().forEach(i -> i.getProduct().decreaseStock(i.getQuantity()));
    }

    public void saveOrder(Order order) {
        if (order != null) {
            orders.add(order);
        }
    }

    public List<Order> getUserOrders(User user) {
        if (user == null) return new ArrayList<>();
        return orders.stream().filter(o -> o.getUser().getId().equals(user.getId())).toList();
    }

    public Order findOrderById(String orderId) {
        return orders.stream().filter(o -> o.getOrderId().equals(orderId)).findFirst().orElse(null);
    }
}