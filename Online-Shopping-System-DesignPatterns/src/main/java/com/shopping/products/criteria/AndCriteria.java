package com.shopping.products.criteria;

import com.shopping.models.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Composite (AND): all children must match.
 */
public class AndCriteria implements ProductCriteria {
    private final List<ProductCriteria> children = new ArrayList<>();

    public AndCriteria(ProductCriteria... items) {
        children.addAll(Arrays.asList(items));
    }

    public AndCriteria add(ProductCriteria c) {
        if (c != null) children.add(c);
        return this;
    }

    @Override
    public boolean test(Product p) {
        for (ProductCriteria c : children) {
            if (!c.test(p)) return false;
        }
        return true;
    }
}
