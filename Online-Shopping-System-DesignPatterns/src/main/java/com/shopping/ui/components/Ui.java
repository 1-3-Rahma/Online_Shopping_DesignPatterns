package com.shopping.ui.components;

import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public final class Ui {
    private Ui() {}

    public static JPanel page(String title, String subtitle) {
        JPanel root = new JPanel(new BorderLayout(0, 16));
        root.setBorder(new EmptyBorder(18, 18, 18, 18));

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setOpaque(false);

        JLabel t = new JLabel(title);
        t.putClientProperty(FlatClientProperties.STYLE, "font:+8");
        JLabel s = new JLabel(subtitle);
        s.putClientProperty(FlatClientProperties.STYLE, "foreground:$Label.disabledForeground");

        header.add(t);
        header.add(Box.createVerticalStrut(4));
        header.add(s);

        root.add(header, BorderLayout.NORTH);
        return root;
    }

    public static JPanel card() {
        JPanel p = new JPanel();
        p.putClientProperty(FlatClientProperties.STYLE, "arc:18;");
        p.setBorder(new EmptyBorder(14, 14, 14, 14));
        return p;
    }

    public static JButton primaryButton(String text) {
        JButton b = new JButton(text);
        b.putClientProperty(FlatClientProperties.STYLE, "arc:14;");
        return b;
    }

    public static JButton ghostButton(String text) {
        JButton b = new JButton(text);
        b.putClientProperty(FlatClientProperties.STYLE, "arc:14;");
        b.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_BORDERLESS);
        return b;
    }

    public static JTextField textField(String placeholder) {
        JTextField tf = new JTextField();
        tf.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, placeholder);
        tf.putClientProperty(FlatClientProperties.STYLE, "arc:14;");
        return tf;
    }

    public static JSpinner intSpinner(int value, int min, int max) {
        SpinnerNumberModel model = new SpinnerNumberModel(value, min, max, 1);
        JSpinner sp = new JSpinner(model);
        ((JSpinner.DefaultEditor) sp.getEditor()).getTextField().setColumns(4);
        sp.putClientProperty(FlatClientProperties.STYLE, "arc:14;");
        return sp;
    }

    public static void info(Component parent, String msg, String title) {
        JOptionPane.showMessageDialog(parent, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void error(Component parent, String msg, String title) {
        JOptionPane.showMessageDialog(parent, msg, title, JOptionPane.ERROR_MESSAGE);
    }
}
