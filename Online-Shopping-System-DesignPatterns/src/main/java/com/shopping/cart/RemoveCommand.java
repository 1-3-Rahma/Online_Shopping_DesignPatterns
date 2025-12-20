package com.shopping.cart;

import com.shopping.models.Product;

public class RemoveCommand implements CartCommand {
    private final ShoppingCart cart;
    private final Product product;
    private final int qty;

    public RemoveCommand(ShoppingCart cart, Product product, int qty) {
        this.cart = cart;
        this.product = product;
        this.qty = qty;
    }

    @Override
    public void execute() { cart.remove(product, qty); }

    @Override
    public void undo() { cart.add(product, qty); }
}
