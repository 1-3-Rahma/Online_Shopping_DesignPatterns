package com.shopping.products;

import com.shopping.models.Product;

import java.util.*;

public class ProductManager {
    private static final ProductManager INSTANCE = new ProductManager();
    private final Map<String, Product> products = new HashMap<>();

    private ProductManager() {}

    public static ProductManager getInstance() { return INSTANCE; }

    public synchronized boolean addProduct(Product p) {
        if (p == null) throw new IllegalArgumentException("product required");
        if (products.containsKey(p.getId())) return false;
        products.put(p.getId(), p);
        return true;
    }

    public synchronized Product findById(String id) {
        if (id == null) return null;
        return products.get(id.trim());
    }

    public synchronized List<Product> listAll() {
        return products.values().stream()
                .sorted(Comparator.comparing(Product::getId))
                .toList();
    }

    public synchronized boolean isEmpty() { return products.isEmpty(); }

    public synchronized boolean deleteProduct(String id) {
        if (id == null) return false;
        return products.remove(id.trim()) != null;
    }
}
