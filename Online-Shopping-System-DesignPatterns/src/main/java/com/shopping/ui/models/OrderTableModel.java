package com.shopping.ui.models;

import com.shopping.models.Order;

import javax.swing.table.AbstractTableModel;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrderTableModel extends AbstractTableModel {
    private final String[] cols = {"Order ID", "Date", "Total", "Payment Method"};
    private List<Order> data = new ArrayList<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");

    public void setData(List<Order> orders) {
        data = new ArrayList<>(orders);
        fireTableDataChanged();
    }

    public Order getAt(int row) {
        if (row < 0 || row >= data.size()) return null;
        return data.get(row);
    }

    @Override public int getRowCount() { return data.size(); }
    @Override public int getColumnCount() { return cols.length; }
    @Override public String getColumnName(int column) { return cols[column]; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Order o = data.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> o.getOrderId();
            case 1 -> o.getCreatedAt().format(formatter);
            case 2 -> String.format("%.2f", o.getTotal());
            case 3 -> o.getPayment().getMethodName();
            default -> "";
        };
    }
}
