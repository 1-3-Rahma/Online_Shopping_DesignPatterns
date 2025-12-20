package com.shopping.cart;

/**
 * Command Pattern (enhanced): supports execute + undo for Undo/Redo.
 */
public interface CartCommand {
    void execute();
    void undo();
}
