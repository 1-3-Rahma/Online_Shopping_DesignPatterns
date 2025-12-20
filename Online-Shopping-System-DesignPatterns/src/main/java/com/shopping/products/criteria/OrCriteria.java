package com.shopping.products.criteria;

import com.shopping.models.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrCriteria implements ProductCriteria {
    private final List<ProductCriteria> children = new ArrayList<>();

    public OrCriteria(ProductCriteria... items) {
        children.addAll(Arrays.asList(items));
    }

    public OrCriteria add(ProductCriteria c) {
        if (c != null) children.add(c);
        return this;
    }

    @Override
    public boolean test(Product p) {
        if (children.isEmpty()) return true;
        for (ProductCriteria c : children) {
            if (c.test(p)) return true;
        }
        return false;
    }
}
