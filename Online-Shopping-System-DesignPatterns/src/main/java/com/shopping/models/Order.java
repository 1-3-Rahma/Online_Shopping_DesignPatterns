package com.shopping.models;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class Order {
    private final String orderId;
    private final User user;
    private final List<CartItem> items;
    private final double total;
    private final String address;
    private final Payment payment;
    private final LocalDateTime createdAt;

    public Order(String orderId, User user, List<CartItem> items, double total, String address, Payment payment) {
        this.orderId = orderId;
        this.user = user;
        this.items = List.copyOf(items);
        this.total = total;
        this.address = address;
        this.payment = payment;
        this.createdAt = LocalDateTime.now();
    }

    public String getOrderId() { return orderId; }
    public User getUser() { return user; }
    public List<CartItem> getItems() { return Collections.unmodifiableList(items); }
    public double getTotal() { return total; }
    public String getAddress() { return address; }
    public Payment getPayment() { return payment; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        return "Order{id=" + orderId + ", user=" + user.getName() + ", total=" + String.format("%.2f", total) + ", items=" + items.size() + ", createdAt=" + createdAt + "}";
    }
}
