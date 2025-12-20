package com.shopping.cart;


public interface CartCommand {
    void execute();
    void undo();
}
