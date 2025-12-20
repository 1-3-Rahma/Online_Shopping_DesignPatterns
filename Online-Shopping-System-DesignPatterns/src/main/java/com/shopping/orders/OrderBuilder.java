package com.shopping.orders;

import com.shopping.models.CartItem;
import com.shopping.models.Order;
import com.shopping.models.Payment;
import com.shopping.models.User;
import com.shopping.util.Ids;

import java.util.ArrayList;
import java.util.List;

public class OrderBuilder {
    private User user;
    private List<CartItem> items = new ArrayList<>();
    private String address;
    private Payment payment;

    public OrderBuilder user(User user) { this.user = user; return this; }
    public OrderBuilder items(List<CartItem> items) { this.items = new ArrayList<>(items); return this; }
    public OrderBuilder address(String address) { this.address = address; return this; }
    public OrderBuilder payment(Payment payment) { this.payment = payment; return this; }

    public Order build() {
        double total = items.stream().mapToDouble(CartItem::getTotal).sum();
        return new Order(Ids.shortId("ORD"), user, items, total, address, payment);
    }
}
