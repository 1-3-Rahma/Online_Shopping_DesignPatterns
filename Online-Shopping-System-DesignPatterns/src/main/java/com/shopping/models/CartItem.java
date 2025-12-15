package com.shopping.models;

public class CartItem {
    private final Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        if (product == null) throw new IllegalArgumentException("product required");
        if (quantity <= 0) throw new IllegalArgumentException("quantity must be > 0");
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }

    public void add(int qty) {
        if (qty <= 0) throw new IllegalArgumentException("qty must be > 0");
        quantity += qty;
    }

    public void remove(int qty) {
        if (qty <= 0) throw new IllegalArgumentException("qty must be > 0");
        if (qty > quantity) throw new IllegalArgumentException("cannot remove more than existing quantity");
        quantity -= qty;
    }

    public double getTotal() {
        return product.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return product.getName() + " x" + quantity + " => " + String.format("%.2f", getTotal());
    }
}
