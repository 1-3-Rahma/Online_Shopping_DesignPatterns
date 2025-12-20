package com.shopping.ui.models;

import com.shopping.models.Product;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ProductTableModel extends AbstractTableModel {
    private final String[] cols = {"ID", "Name", "Category", "Price", "Stock", "Details"};
    private List<Product> data = new ArrayList<>();

    public void setData(List<Product> products) {
        data = new ArrayList<>(products);
        fireTableDataChanged();
    }

    public Product getAt(int row) {
        if (row < 0 || row >= data.size()) return null;
        return data.get(row);
    }

    @Override public int getRowCount() { return data.size(); }
    @Override public int getColumnCount() { return cols.length; }
    @Override public String getColumnName(int column) { return cols[column]; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Product p = data.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> p.getId();
            case 1 -> p.getName();
            case 2 -> p.getCategory();
            case 3 -> String.format("%.2f", p.getPrice());
            case 4 -> p.getStock();
            case 5 -> p.getDetails();
            default -> "";
        };
    }
}
