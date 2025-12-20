package com.shopping.models;

import java.util.Objects;
// ProductModel
public abstract class Product {
    private final String id;
    private final String name;
    private final double price;
    private int stock;

    protected Product(String id, String name, double price, int stock) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("Product id required");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Product name required");
        if (price < 0) throw new IllegalArgumentException("Price cannot be negative");
        if (stock < 0) throw new IllegalArgumentException("Stock cannot be negative");
        this.id = id.trim();
        this.name = name.trim();
        this.price = price;
        this.stock = stock;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }

    public boolean hasStock(int qty) { return qty > 0 && stock >= qty; }

    public void decreaseStock(int qty) {
        if (!hasStock(qty)) throw new IllegalStateException("Insufficient stock for " + name);
        stock -= qty;
    }

    public void increaseStock(int qty) {
        if (qty <= 0) throw new IllegalArgumentException("qty must be > 0");
        stock += qty;
    }

    public abstract String getCategory();
    public abstract String getDetails();

    @Override
    public String toString() {
        return String.format("[%s] %s (id=%s, price=%.2f, stock=%d) %s",
                getCategory(), name, id, price, stock, getDetails());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return id.equals(product.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
