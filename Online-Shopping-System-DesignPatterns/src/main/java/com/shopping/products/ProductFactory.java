package com.shopping.products;

import com.shopping.models.Product;

public class ProductFactory {
    public enum ProductType { ELECTRONICS, CLOTHING, BOOK }

    public Product create(ProductType type, String id, String name, double price, int stock, String extra) {
        return switch (type) {
            case ELECTRONICS -> new Electronics(id, name, price, stock, extra); // extra=brand
            case CLOTHING -> new Clothing(id, name, price, stock, extra);       // extra=size
            case BOOK -> new Book(id, name, price, stock, extra);              // extra=author
        };
    }
}
