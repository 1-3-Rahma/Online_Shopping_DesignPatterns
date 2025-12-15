package com.shopping.cart;

import com.shopping.models.CartItem;
import com.shopping.models.Product;

import java.util.*;

public class ShoppingCart {
    private static final ShoppingCart INSTANCE = new ShoppingCart();
    private final Map<String, CartItem> items = new HashMap<>();

    private ShoppingCart() {}

    public static ShoppingCart getInstance() { return INSTANCE; }

    public void add(Product product, int qty) {
        if (product == null) throw new IllegalArgumentException("product required");
        if (qty <= 0) throw new IllegalArgumentException("qty must be > 0");

        CartItem existing = items.get(product.getId());
        if (existing == null) items.put(product.getId(), new CartItem(product, qty));
        else existing.add(qty);
    }

    public void remove(Product product, int qty) {
        if (product == null) throw new IllegalArgumentException("product required");
        if (qty <= 0) throw new IllegalArgumentException("qty must be > 0");

        CartItem existing = items.get(product.getId());
        if (existing == null) return;

        if (qty >= existing.getQuantity()) items.remove(product.getId());
        else existing.remove(qty);
    }

    public List<CartItem> getItems() {
        return items.values().stream().toList();
    }

    public boolean isEmpty() { return items.isEmpty(); }

    public double getTotal() {
        return items.values().stream().mapToDouble(CartItem::getTotal).sum();
    }

    public void clear() { items.clear(); }

    public CartItem findItem(String productId) {
        return items.get(productId);
    }
}
