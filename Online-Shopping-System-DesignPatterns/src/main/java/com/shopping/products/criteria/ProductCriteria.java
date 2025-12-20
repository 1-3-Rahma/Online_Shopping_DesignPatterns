package com.shopping.products.criteria;

import com.shopping.models.Product;

@FunctionalInterface
public interface ProductCriteria {
    boolean test(Product p);

    default ProductCriteria and(ProductCriteria other) {
        return new AndCriteria(this, other);
    }

    default ProductCriteria or(ProductCriteria other) {
        return new OrCriteria(this, other);
    }

    static ProductCriteria all() { return p -> true; }
}
