package com.shopping.ui;

import com.formdev.flatlaf.FlatClientProperties;
import com.shopping.controllers.*;
import com.shopping.models.*;
import com.shopping.payment.PaymentFactory;
import com.shopping.products.criteria.*;
import com.shopping.ui.theme.ThemeManager;
import com.shopping.ui.components.Ui;
import com.shopping.ui.models.CartTableModel;
import com.shopping.ui.models.ProductTableModel;
import com.shopping.ui.models.OrderTableModel;

import javax.swing.*;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class CustomerFrame extends JFrame {

    private final AuthController auth = new AuthController();
    private final ProductController products = new ProductController();
    private final CartController cart = new CartController();
    private final PaymentController payment = new PaymentController();
    private final OrderController orders = new OrderController();

    private final CriteriaFactory criteriaFactory = new CriteriaFactory();
    private final SortFactory sortFactory = new SortFactory();


    private final ProductTableModel productModel = new ProductTableModel();
    private final CartTableModel cartModel = new CartTableModel();
    private final OrderTableModel orderModel = new OrderTableModel();

    private JTable productTable;
    private JTable cartTable;
    private JTable orderTable;
    private JLabel totalLabel;

    private JTextField searchField;
    private JComboBox<String> categoryBox;
    private JComboBox<String> sortBox;
    private JButton undoBtn;
    private JButton redoBtn;
    private JToggleButton themeToggle;

    public CustomerFrame() {
        super("ShopSmart — Customer");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1100, 700));
        setLocationRelativeTo(null);

        ThemeManager.getInstance().apply(this);
        seedIfEmpty();
        setContentPane(buildUi());
        refreshProducts();
        refreshCart();
        refreshOrders();
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

        User u = auth.currentUser();
        JLabel title = new JLabel("Shopping Dashboard");
        title.putClientProperty(FlatClientProperties.STYLE, "font:+8");

        JLabel user = new JLabel(u != null ? (u.getName() + " • " + u.getEmail()) : "");
        user.putClientProperty(FlatClientProperties.STYLE, "foreground:$Label.disabledForeground");

        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.add(title);
        left.add(Box.createVerticalStrut(4));
        left.add(user);

        JButton logout = Ui.ghostButton("Logout");
        logout.addActionListener(e -> {
            auth.logout();
            dispose();
            new LoginFrame().setVisible(true);
        });

        themeToggle = new JToggleButton("Dark mode");
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
        JTabbedPane tabs = new JTabbedPane();
        tabs.putClientProperty(FlatClientProperties.STYLE, "arc:16;");
        tabs.addTab("Shopping", buildShoppingPanel());
        tabs.addTab("My Orders", buildOrdersPanel());
        return tabs;
    }

    private JComponent buildShoppingPanel() {
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        split.setResizeWeight(0.62);
        split.putClientProperty(FlatClientProperties.STYLE, "dividerSize:8;");
        split.setLeftComponent(buildProductsPanel());
        split.setRightComponent(buildCartPanel());
        return split;
    }

    private JComponent buildProductsPanel() {
        JPanel page = Ui.page("Products", "Browse and add items to your cart.");
        JPanel card = Ui.card();
        card.setLayout(new BorderLayout(10, 10));

        productTable = new JTable(productModel);
        productTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        productTable.getColumnModel().getColumn(0).setPreferredWidth(90);   // ID
        productTable.getColumnModel().getColumn(1).setPreferredWidth(180);  // Name
        productTable.getColumnModel().getColumn(2).setPreferredWidth(120);  // Category
        productTable.getColumnModel().getColumn(3).setPreferredWidth(110);  // Price
        productTable.getColumnModel().getColumn(4).setPreferredWidth(80);   // Stock
        productTable.getColumnModel().getColumn(5).setPreferredWidth(320);  // Details (wider)

        productTable.setRowHeight(30);
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane sp = new JScrollPane(productTable);
        sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        sp.putClientProperty(FlatClientProperties.STYLE, "arc:16;");

        // Search / Filter / Sort (Composite + Factory Method)
        JPanel filters = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        filters.setOpaque(false);

        searchField = Ui.textField("Search by name or ID...");
        searchField.setColumns(18);

        categoryBox = new JComboBox<>();
        rebuildCategories();
        categoryBox.putClientProperty(FlatClientProperties.STYLE, "arc:14;");

        sortBox = new JComboBox<>(new String[]{"Name (A-Z)", "Price (Low→High)", "Price (High→Low)", "Stock (High→Low)"});
        sortBox.putClientProperty(FlatClientProperties.STYLE, "arc:14;");

        JButton apply = Ui.ghostButton("Apply");
        apply.addActionListener(e -> refreshProducts());

        filters.add(searchField);
        filters.add(new JLabel("Category"));
        filters.add(categoryBox);
        filters.add(new JLabel("Sort"));
        filters.add(sortBox);
        filters.add(apply);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actions.setOpaque(false);

        JSpinner qty = Ui.intSpinner(1, 1, 999);
        JButton add = Ui.primaryButton("Add to cart");
        JButton refresh = Ui.ghostButton("Refresh");

        refresh.addActionListener(e -> refreshProducts());

        add.addActionListener(e -> {
            int row = productTable.getSelectedRow();
            if (row < 0) { Ui.error(this, "Select a product first.", "No selection"); return; }
            Product p = productModel.getAt(row);
            int q = (Integer) qty.getValue();
            if (p.getStock() <= 0) { Ui.error(this, "This product is out of stock.", "Out of stock"); return; }
            if (!p.hasStock(q)) { Ui.error(this, "Not enough stock. Available: " + p.getStock(), "Stock"); return; }
            cart.addToCart(p, q);
            refreshCart();
            Ui.info(this, "Added to cart successfully.", "Done");
        });

        actions.add(new JLabel("Qty"));
        actions.add(qty);
        actions.add(refresh);
        actions.add(add);

        card.add(filters, BorderLayout.NORTH);
        card.add(sp, BorderLayout.CENTER);
        card.add(actions, BorderLayout.SOUTH);

        page.add(card, BorderLayout.CENTER);
        return page;
    }

    private JComponent buildCartPanel() {
        JPanel page = Ui.page("Your Cart", "Review items, remove, and checkout.");
        JPanel card = Ui.card();
        card.setLayout(new BorderLayout(10, 10));

        cartTable = new JTable(cartModel);
        cartTable.setRowHeight(30);
        cartTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane sp = new JScrollPane(cartTable);
        sp.putClientProperty(FlatClientProperties.STYLE, "arc:16;");

        totalLabel = new JLabel("Total: 0.00");
        totalLabel.putClientProperty(FlatClientProperties.STYLE, "font:+4");

        undoBtn = Ui.ghostButton("Undo");
        redoBtn = Ui.ghostButton("Redo");
        JButton remove = Ui.ghostButton("Remove selected");
        JButton clear = Ui.ghostButton("Clear cart");
        JButton checkout = Ui.primaryButton("Checkout");

        undoBtn.addActionListener(e -> { cart.undo(); refreshCart(); });
        redoBtn.addActionListener(e -> { cart.redo(); refreshCart(); });

        remove.addActionListener(e -> {
            int row = cartTable.getSelectedRow();
            if (row < 0) { Ui.error(this, "Select an item to remove.", "No selection"); return; }
            CartItem item = cartModel.getAt(row);
            String qtyStr = JOptionPane.showInputDialog(this, "Remove quantity (1-" + item.getQuantity() + "):", "1");
            if (qtyStr == null) return;
            try {
                int q = Integer.parseInt(qtyStr.trim());
                if (q < 1 || q > item.getQuantity()) throw new NumberFormatException();
                cart.removeFromCart(item.getProduct(), q);
                refreshCart();
            } catch (Exception ex) {
                Ui.error(this, "Invalid quantity.", "Error");
            }
        });

        clear.addActionListener(e -> {
            if (cart.isEmpty()) return;
            int ok = JOptionPane.showConfirmDialog(this, "Clear the cart?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (ok == JOptionPane.YES_OPTION) {
                cart.clear();
                refreshCart();
            }
        });

        checkout.addActionListener(e -> {
            if (cart.isEmpty()) { Ui.error(this, "Your cart is empty.", "Checkout"); return; }
            openCheckoutDialog();
        });

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBorder(new EmptyBorder(0, 0, 0, 12));
        bottom.setOpaque(false);

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        left.setOpaque(false);
        left.add(totalLabel);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        right.setOpaque(false);
        right.add(undoBtn);
        right.add(redoBtn);
        right.add(remove);
        right.add(clear);
        right.add(checkout);

        bottom.add(left, BorderLayout.WEST);
        bottom.add(right, BorderLayout.EAST);

        card.add(sp, BorderLayout.CENTER);
        card.add(bottom, BorderLayout.SOUTH);

        page.add(card, BorderLayout.CENTER);
        return page;
    }

    private JComponent buildOrdersPanel() {
        JPanel page = Ui.page("My Orders", "View your order history and details.");
        JPanel card = Ui.card();
        card.setLayout(new BorderLayout(10, 10));

        orderTable = new JTable(orderModel);
        orderTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        orderTable.getColumnModel().getColumn(0).setPreferredWidth(120);  // Order ID
        orderTable.getColumnModel().getColumn(1).setPreferredWidth(180);  // Date
        orderTable.getColumnModel().getColumn(2).setPreferredWidth(120);  // Total
        orderTable.getColumnModel().getColumn(3).setPreferredWidth(140);  // Payment Method

        orderTable.setRowHeight(30);
        orderTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane sp = new JScrollPane(orderTable);
        sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        sp.putClientProperty(FlatClientProperties.STYLE, "arc:16;");

        JButton refresh = Ui.ghostButton("Refresh");
        refresh.addActionListener(e -> refreshOrders());

        JButton viewItems = Ui.primaryButton("View items");
        viewItems.addActionListener(e -> {
            int row = orderTable.getSelectedRow();
            if (row < 0) { Ui.error(this, "Select an order to view.", "No selection"); return; }
            Order order = orderModel.getAt(row);
            if (order != null) openOrderDetailsDialog(order);
        });

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        bottom.setOpaque(false);
        bottom.add(refresh);
        bottom.add(viewItems);

        card.add(sp, BorderLayout.CENTER);
        card.add(bottom, BorderLayout.SOUTH);

        page.add(card, BorderLayout.CENTER);
        return page;
    }

    private void openOrderDetailsDialog(Order order) {
        JDialog dialog = new JDialog(this, "Order Details - " + order.getOrderId(), true);
        dialog.setMinimumSize(new Dimension(500, 400));
        dialog.setLocationRelativeTo(this);

        JPanel root = Ui.card();
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBorder(new EmptyBorder(16, 16, 16, 16));

        JLabel title = new JLabel("Order #" + order.getOrderId());
        title.putClientProperty(FlatClientProperties.STYLE, "font:+8");

        JLabel info = new JLabel("Date: " + order.getCreatedAt().format(java.time.format.DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm")) +
                " | Total: " + String.format("%.2f", order.getTotal()) +
                " | Payment: " + order.getPayment().getMethodName() +
                " | Address: " + order.getAddress());
        info.putClientProperty(FlatClientProperties.STYLE, "foreground:$Label.disabledForeground");

        // Items table
        String[] cols = {"Product", "Qty", "Unit Price", "Total"};
        Object[][] data = new Object[order.getItems().size()][4];
        for (int i = 0; i < order.getItems().size(); i++) {
            var item = order.getItems().get(i);
            data[i][0] = item.getProduct().getName();
            data[i][1] = item.getQuantity();
            data[i][2] = String.format("%.2f", item.getProduct().getPrice());
            data[i][3] = String.format("%.2f", item.getTotal());
        }

        JTable itemsTable = new JTable(data, cols);
        itemsTable.setRowHeight(25);
        itemsTable.setEnabled(false);
        itemsTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        itemsTable.getColumnModel().getColumn(1).setPreferredWidth(60);
        itemsTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        itemsTable.getColumnModel().getColumn(3).setPreferredWidth(100);

        JScrollPane sp = new JScrollPane(itemsTable);
        sp.putClientProperty(FlatClientProperties.STYLE, "arc:16;");

        JButton close = Ui.primaryButton("Close");
        close.addActionListener(e -> dialog.dispose());

        root.add(title);
        root.add(Box.createVerticalStrut(6));
        root.add(info);
        root.add(Box.createVerticalStrut(16));
        root.add(new JLabel("Items ordered:"));
        root.add(Box.createVerticalStrut(8));
        root.add(sp);
        root.add(Box.createVerticalStrut(12));
        root.add(close);

        dialog.setContentPane(root);
        dialog.setVisible(true);
    }

    private void openCheckoutDialog() {
        JDialog dialog = new JDialog(this, "Checkout", true);
        dialog.setMinimumSize(new Dimension(520, 520));
        dialog.setLocationRelativeTo(this);

        JPanel root = Ui.card();
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBorder(new EmptyBorder(16, 16, 16, 16));

        JLabel title = new JLabel("Checkout");
        title.putClientProperty(FlatClientProperties.STYLE, "font:+10");
        JLabel sub = new JLabel("Enter shipping info and choose payment method.");
        sub.putClientProperty(FlatClientProperties.STYLE, "foreground:$Label.disabledForeground");

        JTextField address = Ui.textField("Delivery address (e.g. Cairo, Egypt)");
        JComboBox<String> payType = new JComboBox<>(new String[]{"Credit Card", "PayPal", "Legacy Gateway (Adapter)"});
        payType.putClientProperty(FlatClientProperties.STYLE, "arc:14;");
        JTextField credential = Ui.textField("Card: ****-1234 | PayPal: email | Legacy: token");

        JLabel amount = new JLabel("Amount: " + String.format("%.2f", cart.total()));
        amount.putClientProperty(FlatClientProperties.STYLE, "font:+3");

        JButton cancel = Ui.ghostButton("Cancel");
        JButton place = Ui.primaryButton("Place order");

        cancel.addActionListener(e -> dialog.dispose());

        place.addActionListener(e -> {
            String addr = address.getText().trim();
            String cred = credential.getText().trim();
            if (addr.isBlank()) { Ui.error(dialog, "Please enter a delivery address.", "Missing"); return; }
            if (cred.isBlank()) { Ui.error(dialog, "Please enter payment credential.", "Missing"); return; }

            PaymentFactory.PaymentType type = switch (payType.getSelectedIndex()) {
                case 0 -> PaymentFactory.PaymentType.CREDIT_CARD;
                case 1 -> PaymentFactory.PaymentType.PAYPAL;
                default -> PaymentFactory.PaymentType.LEGACY_GATEWAY;
            };

            Payment pay = payment.pay(type, cred, cart.total());

            User user = auth.currentUser();
            Order order = orders.buildOrder(user, cart.items(), addr, pay);

            boolean ok = orders.validate(order);
            if (!ok) {
                Ui.error(dialog, "Order validation failed (stock or payment).", "Checkout failed");
                return;
            }

            orders.applyStock(order);
            orders.saveOrder(order);
            cart.clear();
            refreshProducts();
            refreshCart();

            Ui.info(this, "✅ Order placed!\nOrder ID: " + order.getOrderId(), "Success");
            dialog.dispose();
        });

        root.add(title);
        root.add(Box.createVerticalStrut(6));
        root.add(sub);
        root.add(Box.createVerticalStrut(18));
        root.add(new JLabel("Shipping address"));
        root.add(Box.createVerticalStrut(6));
        root.add(address);
        root.add(Box.createVerticalStrut(12));
        root.add(new JLabel("Payment method"));
        root.add(Box.createVerticalStrut(6));
        root.add(payType);
        root.add(Box.createVerticalStrut(12));
        root.add(new JLabel("Credential"));
        root.add(Box.createVerticalStrut(6));
        root.add(credential);
        root.add(Box.createVerticalStrut(16));
        root.add(amount);
        root.add(Box.createVerticalStrut(18));

        JPanel actions = new JPanel(new GridLayout(1, 2, 10, 0));
        actions.setOpaque(false);
        actions.add(cancel);
        actions.add(place);
        root.add(actions);

        dialog.setContentPane(root);
        dialog.pack();
        dialog.setVisible(true);
    }

    private void rebuildCategories() {
        if (categoryBox == null) return;
        Object selected = categoryBox.getSelectedItem();
        categoryBox.removeAllItems();
        categoryBox.addItem("All");

        for (var p : products.listProducts()) {
            String c = p.getCategory();
            boolean exists = false;
            for (int i = 0; i < categoryBox.getItemCount(); i++) {
                if (String.valueOf(categoryBox.getItemAt(i)).equalsIgnoreCase(c)) { exists = true; break; }
            }
            if (!exists) categoryBox.addItem(c);
        }

        if (selected != null) {
            for (int i = 0; i < categoryBox.getItemCount(); i++) {
                if (String.valueOf(categoryBox.getItemAt(i)).equalsIgnoreCase(String.valueOf(selected))) {
                    categoryBox.setSelectedIndex(i);
                    return;
                }
            }
        }
        categoryBox.setSelectedIndex(0);
    }

private void refreshProducts() {
        rebuildCategories();
        ProductCriteria criteria = ProductCriteria.all();

        // Leaf criteria (Factory Method) + Composite AND
        criteria = criteria.and(criteriaFactory.nameContains(searchField != null ? searchField.getText() : ""))
                           .and(criteriaFactory.category(categoryBox != null ? (String) categoryBox.getSelectedItem() : "All"));

        var list = products.listProducts().stream().filter(criteria::test).toList();

        SortFactory.SortType sortType = SortFactory.SortType.NAME_AZ;
        if (sortBox != null) {
            sortType = switch (sortBox.getSelectedIndex()) {
                case 1 -> SortFactory.SortType.PRICE_LOW_HIGH;
                case 2 -> SortFactory.SortType.PRICE_HIGH_LOW;
                case 3 -> SortFactory.SortType.STOCK_HIGH_LOW;
                default -> SortFactory.SortType.NAME_AZ;
            };
        }
        var comp = sortFactory.create(sortType);
        list = list.stream().sorted(comp).toList();

        productModel.setData(list);
    }
    private void refreshCart() {
        cartModel.setData(cart.items());
        totalLabel.setText("Total: " + String.format("%.2f", cart.total()));
        if (undoBtn != null) undoBtn.setEnabled(cart.canUndo());
        if (redoBtn != null) redoBtn.setEnabled(cart.canRedo());
    }

    private void refreshOrders() {
        User user = auth.currentUser();
        if (user == null) {
            orderModel.setData(new ArrayList<>());
            return;
        }
        orderModel.setData(orders.getUserOrders(user));
    }
}
