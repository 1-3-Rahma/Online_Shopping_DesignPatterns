package com.shopping.cart;

import com.shopping.models.Product;

public class AddCommand implements CartCommand {
    private final ShoppingCart cart;
    private final Product product;
    private final int qty;

    public AddCommand(ShoppingCart cart, Product product, int qty) {
        this.cart = cart;
        this.product = product;
        this.qty = qty;
    }

    @Override
    public void execute() {
        cart.add(product, qty);
    }
}
