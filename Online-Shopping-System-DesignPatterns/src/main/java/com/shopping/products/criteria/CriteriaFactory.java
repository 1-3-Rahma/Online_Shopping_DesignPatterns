package com.shopping.products.criteria;

public class CriteriaFactory {

    public ProductCriteria nameContains(String text) {
        return new NameContainsCriteria(text);
    }

    public ProductCriteria category(String category) {
        return new CategoryCriteria(category);
    }

    public ProductCriteria priceRange(Double min, Double max) {
        return new PriceRangeCriteria(min, max);
    }
}
