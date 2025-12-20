package com.shopping.products;

import com.shopping.models.Product;

public class GenericProduct extends Product {
    private final String category;
    private final String details;

    public GenericProduct(String id, String name, double price, int stock, String category, String details) {
        super(id, name, price, stock);
        this.category = (category == null || category.isBlank()) ? "General" : category.trim();
        this.details = (details == null) ? "" : details.trim();
    }

    @Override public String getCategory() { return category; }

    @Override
    public String getDetails() {
        return details.isBlank() ? "(no details)" : details;
    }
}
