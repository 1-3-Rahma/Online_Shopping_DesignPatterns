package com.shopping;

import com.shopping.ui.LoginFrame;
import com.shopping.ui.theme.ThemeManager;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Set default theme (Light)
            ThemeManager.getInstance().setDark(false, new JFrame()); 
            new LoginFrame().setVisible(true);
        });
    }
}
