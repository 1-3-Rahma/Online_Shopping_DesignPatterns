package com.shopping.products.criteria;

import com.shopping.models.Product;

public class CategoryCriteria implements ProductCriteria {
    private final String category;

    public CategoryCriteria(String category) {
        this.category = category == null ? "" : category.trim();
    }

    @Override
    public boolean test(Product p) {
        if (category.isEmpty() || "All".equalsIgnoreCase(category)) return true;
        return p.getCategory().equalsIgnoreCase(category);
    }
}
