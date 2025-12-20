package com.shopping.ui;

import com.formdev.flatlaf.FlatClientProperties;
import com.shopping.controllers.AuthController;
import com.shopping.models.User;
import com.shopping.ui.components.Ui;
import com.shopping.ui.theme.ThemeManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final AuthController authController = new AuthController();

    public LoginFrame() {
        super("Online Shopping System");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ThemeManager.getInstance().apply(this);
        setMinimumSize(new Dimension(940, 600));
        setLocationRelativeTo(null);
        setContentPane(buildUi());
    }

    private JComponent buildUi() {
        JPanel root = new JPanel(new BorderLayout());

        JPanel left = new JPanel(new BorderLayout());
        left.setPreferredSize(new Dimension(360, 10));
        left.setBorder(new EmptyBorder(26, 26, 26, 26));
        left.putClientProperty(FlatClientProperties.STYLE,
                "background: linear-gradient(#1D4ED8, #9333EA);");

        JLabel brand = new JLabel("<html><div style='font-size:26px;'><b>ShopSmart</b></div>"
               );
        brand.setForeground(Color.black);

        JLabel hint = new JLabel("<html><div style='margin-top:16px;'>Login as <b>Admin</b> to manage products<br>"
                + "or <b>Customer</b> to shop & checkout.</div></html>");
        hint.setForeground(Color.black);

        left.add(brand, BorderLayout.NORTH);
        left.add(hint, BorderLayout.SOUTH);

        JPanel right = new JPanel(new GridBagLayout());
        right.setBorder(new EmptyBorder(18, 18, 18, 18));

        JPanel card = Ui.card();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(420, 420));

        JLabel title = new JLabel("Welcome");
        title.putClientProperty(FlatClientProperties.STYLE, "font:+10");
        JLabel sub = new JLabel("Sign in to continue");
        sub.putClientProperty(FlatClientProperties.STYLE, "foreground:$Label.disabledForeground");

        JTextField id = Ui.textField("User ID (e.g. U10)");
        JTextField name = Ui.textField("Full name");
        JTextField email = Ui.textField("Email address");

        JComboBox<String> role = new JComboBox<>(new String[]{"Customer", "Admin"});
        role.putClientProperty(FlatClientProperties.STYLE, "arc:14;");

        JButton login = Ui.primaryButton("Login");
        JButton exit = Ui.ghostButton("Exit");

        login.addActionListener(e -> {
            String uid = id.getText().trim();
            String uname = name.getText().trim();
            String uemail = email.getText().trim();
            String r = (String) role.getSelectedItem();

            if (uid.isBlank() || uname.isBlank() || uemail.isBlank()) {
                Ui.error(this, "Please fill in all fields.", "Missing info");
                return;
            }

            try {
                User user = "Admin".equals(r)
                        ? authController.loginAdmin(uid, uname, uemail)
                        : authController.loginCustomer(uid, uname, uemail);

                dispose();
                if ("Admin".equalsIgnoreCase(user.getRole()))
                    new AdminFrame().setVisible(true);
                else
                    new CustomerFrame().setVisible(true);

            } catch (Exception ex) {
                Ui.error(this, ex.getMessage(), "Login failed");
            }
        });

        exit.addActionListener(e -> System.exit(0));

        card.add(title);
        card.add(Box.createVerticalStrut(6));
        card.add(sub);
        card.add(Box.createVerticalStrut(18));

        card.add(new JLabel("Role"));
        card.add(Box.createVerticalStrut(6));
        card.add(role);

        card.add(Box.createVerticalStrut(12));
        card.add(new JLabel("User ID"));
        card.add(Box.createVerticalStrut(6));
        card.add(id);

        card.add(Box.createVerticalStrut(12));
        card.add(new JLabel("Name"));
        card.add(Box.createVerticalStrut(6));
        card.add(name);

        card.add(Box.createVerticalStrut(12));
        card.add(new JLabel("Email"));
        card.add(Box.createVerticalStrut(6));
        card.add(email);

        card.add(Box.createVerticalStrut(18));

        JPanel actions = new JPanel(new GridLayout(1, 2, 10, 0));
        actions.setOpaque(false);
        actions.add(exit);
        actions.add(login);
        card.add(actions);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        right.add(card, gbc);

        root.add(left, BorderLayout.WEST);
        root.add(right, BorderLayout.CENTER);
        return root;
    }
}
