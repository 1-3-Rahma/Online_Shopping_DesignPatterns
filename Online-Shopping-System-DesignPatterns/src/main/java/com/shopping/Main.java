package com.shopping;

import com.shopping.ui.LoginFrame;
import com.shopping.ui.theme.ThemeManager;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Set default theme (Light). You can switch later from the UI.
            ThemeManager.getInstance().setDark(false, new JFrame()); // initialize LAF
            new LoginFrame().setVisible(true);
        });
    }
}
