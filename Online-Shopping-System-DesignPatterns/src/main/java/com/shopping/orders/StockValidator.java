package com.shopping.orders;

import com.shopping.models.CartItem;
import com.shopping.models.Order;

public class StockValidator extends OrderValidator {
    @Override protected boolean check(Order order) {
        for (CartItem item : order.getItems())
            if (!item.getProduct().hasStock(item.getQuantity())) return false;
        return true;
    }
}
