package com.shopping.ui.models;

import com.shopping.models.CartItem;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class CartTableModel extends AbstractTableModel {
    private final String[] cols = {"Product", "Qty", "Unit Price", "Total"};
    private List<CartItem> data = new ArrayList<>();

    public void setData(List<CartItem> items) {
        data = new ArrayList<>(items);
        fireTableDataChanged();
    }

    public CartItem getAt(int row) {
        if (row < 0 || row >= data.size()) return null;
        return data.get(row);
    }

    @Override public int getRowCount() { return data.size(); }
    @Override public int getColumnCount() { return cols.length; }
    @Override public String getColumnName(int column) { return cols[column]; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        CartItem i = data.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> i.getProduct().getName();
            case 1 -> i.getQuantity();
            case 2 -> String.format("%.2f", i.getProduct().getPrice());
            case 3 -> String.format("%.2f", i.getTotal());
            default -> "";
        };
    }
}
