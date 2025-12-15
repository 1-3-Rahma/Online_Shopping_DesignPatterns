package com.shopping.products;

import com.shopping.models.Product;

public class Clothing extends Product {
    private final String size;

    public Clothing(String id, String name, double price, int stock, String size) {
        super(id, name, price, stock);
        this.size = (size == null ? "" : size.trim());
    }

    public String getSize() { return size; }

    @Override
    public String getCategory() { return "Clothing"; }

    @Override
    public String getDetails() { return "(size=" + size + ")"; }
}
