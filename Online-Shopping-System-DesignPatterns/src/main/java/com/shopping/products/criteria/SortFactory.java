package com.shopping.products.criteria;

import com.shopping.models.Product;

import java.util.Comparator;


public class SortFactory {

    public enum SortType {
        NAME_AZ, PRICE_LOW_HIGH, PRICE_HIGH_LOW, STOCK_HIGH_LOW
    }

    public Comparator<Product> create(SortType type) {
        return switch (type) {
            case NAME_AZ -> Comparator.comparing(Product::getName, String.CASE_INSENSITIVE_ORDER);
            case PRICE_LOW_HIGH -> Comparator.comparingDouble(Product::getPrice);
            case PRICE_HIGH_LOW -> Comparator.comparingDouble(Product::getPrice).reversed();
            case STOCK_HIGH_LOW -> Comparator.comparingInt(Product::getStock).reversed();
        };
    }
}
