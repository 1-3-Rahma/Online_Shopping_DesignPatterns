package com.shopping.ui.theme;

import javax.swing.*;

/**
 * Bridge Implementation: applies a theme to Swing.
 */
public interface ThemeApplier {
    void applyLight(JFrame frame);
    void applyDark(JFrame frame);
}
