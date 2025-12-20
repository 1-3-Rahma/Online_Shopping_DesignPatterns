package com.shopping.products;

import com.shopping.models.Product;

public class ProductFactory {
    public enum ProductType { ELECTRONICS, CLOTHING, BOOK, GENERIC }

    public Product create(ProductType type, String id, String name, double price, int stock, String extra) {
        return switch (type) {
            case ELECTRONICS -> new Electronics(id, name, price, stock, extra);
            case CLOTHING -> new Clothing(id, name, price, stock, extra);
            case BOOK -> new Book(id, name, price, stock, extra);
            case GENERIC -> new GenericProduct(id, name, price, stock, "General", extra);
        };
    }


    public Product create(String category, String id, String name, double price, int stock, String details) {
        String c = category == null ? "" : category.trim();
        if (c.equalsIgnoreCase("Electronics")) return new Electronics(id, name, price, stock, details);
        if (c.equalsIgnoreCase("Clothing")) return new Clothing(id, name, price, stock, details);
        if (c.equalsIgnoreCase("Book")) return new Book(id, name, price, stock, details);
        return new GenericProduct(id, name, price, stock, c.isBlank() ? "General" : c, details);
    }
}
