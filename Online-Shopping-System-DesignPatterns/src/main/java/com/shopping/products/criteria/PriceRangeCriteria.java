package com.shopping.products.criteria;

import com.shopping.models.Product;

public class PriceRangeCriteria implements ProductCriteria {
    private final Double min;
    private final Double max;

    public PriceRangeCriteria(Double min, Double max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean test(Product p) {
        double price = p.getPrice();
        if (min != null && price < min) return false;
        if (max != null && price > max) return false;
        return true;
    }
}
