package com.shopping.controllers;

import com.shopping.models.Product;
import com.shopping.products.ProductFactory;
import com.shopping.products.ProductManager;

import java.util.List;

public class ProductController {
    private final ProductManager pm = ProductManager.getInstance();
    private final ProductFactory factory = new ProductFactory();

    public List<Product> listProducts() { return pm.listAll(); }
    public boolean isEmpty() { return pm.isEmpty(); }

    public boolean addProduct(String category, String id, String name, double price, int stock, String details) {
        Product p = factory.create(category, id, name, price, stock, details);
        return pm.addProduct(p);
    }

    public boolean increaseStock(String productId, int qty) {
        Product p = pm.findById(productId);
        if (p == null) return false;
        p.increaseStock(qty);
        return true;
    }

    public boolean deleteProduct(String id) {
        return pm.deleteProduct(id);
    }
}
