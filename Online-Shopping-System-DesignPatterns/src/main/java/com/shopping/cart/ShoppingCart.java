package com.shopping.cart;

import com.shopping.models.CartItem;
import com.shopping.models.Product;

import java.util.*;

public class ShoppingCart {
    private static final ShoppingCart INSTANCE = new ShoppingCart();
    private final Map<String, CartItem> items = new HashMap<>();

    private ShoppingCart() {}

    public static ShoppingCart getInstance() { return INSTANCE; }

    public synchronized void add(Product product, int qty) {
        if (product == null) throw new IllegalArgumentException("product required");
        if (qty <= 0) throw new IllegalArgumentException("qty must be > 0");

        CartItem existing = items.get(product.getId());
        if (existing == null) items.put(product.getId(), new CartItem(product, qty));
        else existing.add(qty);
    }

    public synchronized void remove(Product product, int qty) {
        if (product == null) throw new IllegalArgumentException("product required");
        if (qty <= 0) throw new IllegalArgumentException("qty must be > 0");

        CartItem existing = items.get(product.getId());
        if (existing == null) return;

        if (qty >= existing.getQuantity()) items.remove(product.getId());
        else existing.remove(qty);
    }

    public synchronized List<CartItem> getItems() { return items.values().stream().toList(); }
    public synchronized boolean isEmpty() { return items.isEmpty(); }
    public synchronized double getTotal() { return items.values().stream().mapToDouble(CartItem::getTotal).sum(); }
    public synchronized void clear() { items.clear(); }
    public synchronized CartItem findItem(String productId) { return items.get(productId); }
}
