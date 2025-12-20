

package com.shopping.products.criteria;

import com.shopping.models.Product;

public class NameContainsCriteria implements ProductCriteria {
    private final String needle;

    public NameContainsCriteria(String needle) {
        this.needle = needle == null ? "" : needle.trim().toLowerCase();
    }

    @Override
    public boolean test(Product p) {
        if (needle.isEmpty()) return true;
        return p.getName().toLowerCase().contains(needle) || p.getId().toLowerCase().contains(needle);
    }
}
