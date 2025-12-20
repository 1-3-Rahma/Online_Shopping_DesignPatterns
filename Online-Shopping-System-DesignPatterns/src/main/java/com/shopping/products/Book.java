package com.shopping.products;

import com.shopping.models.Product;

public class Book extends Product {
    private final String author;

    public Book(String id, String name, double price, int stock, String author) {
        super(id, name, price, stock);
        this.author = (author == null ? "" : author.trim());
    }

    @Override public String getCategory() { return "Book"; }
    @Override public String getDetails() { return "(author=" + author + ")"; }
}
