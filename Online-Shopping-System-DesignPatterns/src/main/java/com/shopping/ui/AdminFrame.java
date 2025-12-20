package com.shopping.ui;

import com.formdev.flatlaf.FlatClientProperties;
import com.shopping.controllers.AuthController;
import com.shopping.controllers.ProductController;
import com.shopping.ui.components.Ui;
import com.shopping.ui.models.ProductTableModel;
import com.shopping.ui.theme.ThemeManager;

import javax.swing.*;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AdminFrame extends JFrame {

    private final AuthController auth = new AuthController();
    private final ProductController products = new ProductController();

    private final ProductTableModel productModel = new ProductTableModel();
    private JTable table;

    public AdminFrame() {
        super("ShopSmart — Admin");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1100, 720));
        setLocationRelativeTo(null);

        ThemeManager.getInstance().apply(this);
        seedIfEmpty();
        setContentPane(buildUi());
        refresh();
    }

    private void seedIfEmpty() {
        if (!products.isEmpty()) return;
        products.addProduct("Electronics", "P100", "Headphones", 1200, 10, "Sony");
        products.addProduct("Clothing", "P200", "Hoodie", 800, 5, "L");
        products.addProduct("Book", "P300", "Clean Code", 650, 3, "Robert C. Martin");
    }

    private JComponent buildUi() {
        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBorder(new EmptyBorder(16, 16, 16, 16));
        root.add(buildTopBar(), BorderLayout.NORTH);
        root.add(buildMain(), BorderLayout.CENTER);
        return root;
    }

    private JComponent buildTopBar() {
        JPanel top = Ui.card();
        top.setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Admin Dashboard");
        title.putClientProperty(FlatClientProperties.STYLE, "font:+8");
        JLabel sub = new JLabel("Manage products using Factory Method + Singleton ProductManager.");
        sub.putClientProperty(FlatClientProperties.STYLE, "foreground:$Label.disabledForeground");

        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.add(title);
        left.add(Box.createVerticalStrut(4));
        left.add(sub);

        JButton logout = Ui.ghostButton("Logout");
        logout.addActionListener(e -> {
            auth.logout();
            dispose();
            new LoginFrame().setVisible(true);
        });

        JToggleButton themeToggle = new JToggleButton("Dark mode");
        themeToggle.putClientProperty(FlatClientProperties.STYLE, "arc:14;");
        themeToggle.setSelected(ThemeManager.getInstance().isDark());
        themeToggle.addActionListener(e -> ThemeManager.getInstance().setDark(themeToggle.isSelected(), this));

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actions.setOpaque(false);
        actions.add(themeToggle);
        actions.add(logout);

        top.add(left, BorderLayout.WEST);
        top.add(actions, BorderLayout.EAST);
        return top;
    }

    private JComponent buildMain() {
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        split.setResizeWeight(0.65);
        split.putClientProperty(FlatClientProperties.STYLE, "dividerSize:8;");
        split.setLeftComponent(buildProductsList());
        split.setRightComponent(buildAddProductForm());
        return split;
    }

    private JComponent buildProductsList() {
        JPanel page = Ui.page("Products", "Current catalog in the system.");
        JPanel card = Ui.card();
        card.setLayout(new BorderLayout(10, 10));

        table = new JTable(productModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(90);   // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(180);  // Name
        table.getColumnModel().getColumn(2).setPreferredWidth(120);  // Category
        table.getColumnModel().getColumn(3).setPreferredWidth(110);  // Price
        table.getColumnModel().getColumn(4).setPreferredWidth(80);   // Stock
        table.getColumnModel().getColumn(5).setPreferredWidth(320);  // Details (wider)

        table.setRowHeight(30);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane sp = new JScrollPane(table);
        sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        sp.putClientProperty(FlatClientProperties.STYLE, "arc:16;");

        JButton refresh = Ui.ghostButton("Refresh");
        refresh.addActionListener(e -> refresh());

        JButton edit = Ui.ghostButton("Edit selected");
        JButton delete = Ui.ghostButton("Delete selected");

        edit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) { Ui.error(this, "Select a product from the table first.", "No selection"); return; }
            openEditDialog(row);
        });

        delete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) { Ui.error(this, "Select a product from the table first.", "No selection"); return; }
            String pid = (String) productModel.getValueAt(row, 0);
            int ok = JOptionPane.showConfirmDialog(this, "Delete product " + pid + "?", "Confirm delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (ok == JOptionPane.YES_OPTION) {
                boolean success = products.deleteProduct(pid);
                if (success) {
                    Ui.info(this, "Product deleted successfully.", "Done");
                    refresh();
                } else {
                    Ui.error(this, "Failed to delete product.", "Error");
                }
            }
        });

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        bottom.setOpaque(false);
        bottom.add(refresh);
        bottom.add(edit);
        bottom.add(delete);

        card.add(sp, BorderLayout.CENTER);
        card.add(bottom, BorderLayout.SOUTH);
        page.add(card, BorderLayout.CENTER);
        return page;
    }

    private JComponent buildAddProductForm() {
        JPanel page = Ui.page("Add / Update", "Add new products or increase stock.");
        JPanel card = Ui.card();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JComboBox<String> type = new JComboBox<>(new String[]{"Electronics", "Clothing", "Book"});
        type.setEditable(true);
        type.putClientProperty(FlatClientProperties.STYLE, "arc:14;");

        JTextField id = Ui.textField("Unique product id (e.g. P500)");
        JTextField name = Ui.textField("Product name");
        JTextField price = Ui.textField("Price (e.g. 999.99)");
        JTextField stock = Ui.textField("Stock (e.g. 10)");
        JTextField extra = Ui.textField("Details (e.g. brand, size, author, specs...)");

        JLabel tip = new JLabel("Details is free-text. You can also type a NEW category/type (dynamic).");
        tip.putClientProperty(FlatClientProperties.STYLE, "foreground:$Label.disabledForeground");

        JButton add = Ui.primaryButton("Add product");
        JButton incStock = Ui.ghostButton("Increase stock for selected");

        add.addActionListener(e -> {
            try {
                String category = String.valueOf(type.getEditor().getItem()).trim();

                String pid = id.getText().trim();
                String pname = name.getText().trim();
                double pprice = Double.parseDouble(price.getText().trim());
                int pstock = Integer.parseInt(stock.getText().trim());
                String pextra = extra.getText().trim();

                if (pid.isBlank() || pname.isBlank() || pextra.isBlank())
                    throw new IllegalArgumentException("Please fill required fields.");

                boolean ok = products.addProduct(category, pid, pname, pprice, pstock, pextra);
                if (!ok) Ui.error(this, "Product ID already exists.", "Duplicate");
                else { Ui.info(this, "Product added successfully.", "Done"); refresh(); }
            } catch (Exception ex) {
                Ui.error(this, "Invalid input: " + ex.getMessage(), "Error");
            }
        });

        incStock.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) { Ui.error(this, "Select a product from the table first.", "No selection"); return; }
            String pid = (String) productModel.getValueAt(row, 0);

            String qtyStr = JOptionPane.showInputDialog(this, "Increase stock by:", "1");
            if (qtyStr == null) return;
            try {
                int qty = Integer.parseInt(qtyStr.trim());
                if (qty <= 0) throw new NumberFormatException();
                boolean ok = products.increaseStock(pid, qty);
                if (!ok) Ui.error(this, "Product not found.", "Error");
                else refresh();
            } catch (Exception ex) {
                Ui.error(this, "Invalid quantity.", "Error");
            }
        });

        card.add(new JLabel("Category / Type"));
        card.add(Box.createVerticalStrut(6));
        card.add(type);
        card.add(Box.createVerticalStrut(12));

        card.add(new JLabel("Product ID"));
        card.add(Box.createVerticalStrut(6));
        card.add(id);
        card.add(Box.createVerticalStrut(12));

        card.add(new JLabel("Name"));
        card.add(Box.createVerticalStrut(6));
        card.add(name);
        card.add(Box.createVerticalStrut(12));

        card.add(new JLabel("Price"));
        card.add(Box.createVerticalStrut(6));
        card.add(price);
        card.add(Box.createVerticalStrut(12));

        card.add(new JLabel("Stock"));
        card.add(Box.createVerticalStrut(6));
        card.add(stock);
        card.add(Box.createVerticalStrut(12));

        card.add(new JLabel("Details"));
        card.add(Box.createVerticalStrut(6));
        card.add(extra);
        card.add(Box.createVerticalStrut(10));
        card.add(tip);
        card.add(Box.createVerticalStrut(16));

        JPanel actions = new JPanel(new GridLayout(1, 2, 10, 0));
        actions.setOpaque(false);
        actions.add(incStock);
        actions.add(add);
        card.add(actions);

        page.add(card, BorderLayout.CENTER);
        return page;
    }

    private void openEditDialog(int row) {
        JDialog dialog = new JDialog(this, "Edit Product", true);
        dialog.setMinimumSize(new Dimension(450, 400));
        dialog.setLocationRelativeTo(this);

        String pid = (String) productModel.getValueAt(row, 0);
        var product = products.listProducts().stream().filter(p -> p.getId().equals(pid)).findFirst().orElse(null);
        if (product == null) { Ui.error(this, "Product not found.", "Error"); return; }

        JPanel root = Ui.card();
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBorder(new EmptyBorder(16, 16, 16, 16));

        JLabel title = new JLabel("Edit Product: " + pid);
        title.putClientProperty(FlatClientProperties.STYLE, "font:+8");

        JTextField name = Ui.textField("Product name");
        name.setText(product.getName());

        JTextField price = Ui.textField("Price");
        price.setText(String.format("%.2f", product.getPrice()));

        JTextField extra = Ui.textField("Details");
        extra.setText(product.getDetails());

        JButton cancel = Ui.ghostButton("Cancel");
        JButton save = Ui.primaryButton("Save changes");

        cancel.addActionListener(e -> dialog.dispose());

        save.addActionListener(e -> {
            try {
                String newName = name.getText().trim();
                double newPrice = Double.parseDouble(price.getText().trim());
                String newExtra = extra.getText().trim();

                if (newName.isBlank() || newExtra.isBlank()) {
                    Ui.error(dialog, "Please fill all fields.", "Missing");
                    return;
                }

                if (newPrice < 0) {
                    Ui.error(dialog, "Price cannot be negative.", "Invalid");
                    return;
                }

                // Delete old and add new (simulating edit with immutable fields)
                products.deleteProduct(pid);
                String category = product.getCategory();
                int stock = product.getStock();
                boolean ok = products.addProduct(category, pid, newName, newPrice, stock, newExtra);
                
                if (ok) {
                    Ui.info(dialog, "Product updated successfully.", "Done");
                    refresh();
                    dialog.dispose();
                } else {
                    Ui.error(dialog, "Failed to update product.", "Error");
                }
            } catch (NumberFormatException ex) {
                Ui.error(dialog, "Invalid price format.", "Error");
            }
        });

        root.add(title);
        root.add(Box.createVerticalStrut(16));
        root.add(new JLabel("Product Name"));
        root.add(Box.createVerticalStrut(6));
        root.add(name);
        root.add(Box.createVerticalStrut(12));
        root.add(new JLabel("Price"));
        root.add(Box.createVerticalStrut(6));
        root.add(price);
        root.add(Box.createVerticalStrut(12));
        root.add(new JLabel("Details"));
        root.add(Box.createVerticalStrut(6));
        root.add(extra);
        root.add(Box.createVerticalStrut(16));

        JPanel actions = new JPanel(new GridLayout(1, 2, 10, 0));
        actions.setOpaque(false);
        actions.add(cancel);
        actions.add(save);
        root.add(actions);

        dialog.setContentPane(root);
        dialog.setVisible(true);
    }

    private void refresh() { productModel.setData(products.listProducts()); }
}
