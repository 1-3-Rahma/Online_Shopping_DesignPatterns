package com.shopping.products;

import com.shopping.models.Product;

public class Electronics extends Product {
    private final String brand;

    public Electronics(String id, String name, double price, int stock, String brand) {
        super(id, name, price, stock);
        this.brand = (brand == null ? "" : brand.trim());
    }

    public String getBrand() { return brand; }

    @Override
    public String getCategory() { return "Electronics"; }

    @Override
    public String getDetails() { return "(brand=" + brand + ")"; }
}
