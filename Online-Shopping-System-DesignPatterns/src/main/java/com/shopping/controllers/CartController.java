package com.shopping.controllers;

import com.shopping.cart.AddCommand;
import com.shopping.cart.CartCommand;
import com.shopping.cart.RemoveCommand;
import com.shopping.cart.ShoppingCart;
import com.shopping.models.CartItem;
import com.shopping.models.Product;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class CartController {
    private final ShoppingCart cart = ShoppingCart.getInstance();

    private final Deque<CartCommand> undoStack = new ArrayDeque<>();
    private final Deque<CartCommand> redoStack = new ArrayDeque<>();

    private void run(CartCommand cmd) {
        cmd.execute();
        undoStack.push(cmd);
        redoStack.clear();
    }

    public void addToCart(Product p, int qty) { run(new AddCommand(cart, p, qty)); }
    public void removeFromCart(Product p, int qty) { run(new RemoveCommand(cart, p, qty)); }

    public boolean canUndo() { return !undoStack.isEmpty(); }
    public boolean canRedo() { return !redoStack.isEmpty(); }

    public void undo() {
        if (undoStack.isEmpty()) return;
        CartCommand cmd = undoStack.pop();
        cmd.undo();
        redoStack.push(cmd);
    }

    public void redo() {
        if (redoStack.isEmpty()) return;
        CartCommand cmd = redoStack.pop();
        cmd.execute();
        undoStack.push(cmd);
    }

    public List<CartItem> items() { return cart.getItems(); }
    public double total() { return cart.getTotal(); }
    public boolean isEmpty() { return cart.isEmpty(); }

    public void clear() {
        cart.clear();
        undoStack.clear();
        redoStack.clear();
    }
}
