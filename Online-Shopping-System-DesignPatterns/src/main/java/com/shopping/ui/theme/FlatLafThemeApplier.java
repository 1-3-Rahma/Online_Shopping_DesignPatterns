package com.shopping.ui.theme;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;

public class FlatLafThemeApplier implements ThemeApplier {

    private static boolean fontInitialized = false;


    private void commonPolish() {
        UIManager.put("Component.arc", 18);
        UIManager.put("Button.arc", 16);
        UIManager.put("TextComponent.arc", 16);
        UIManager.put("ScrollBar.thumbArc", 999);
        UIManager.put("ScrollBar.trackArc", 999);
        UIManager.put("Table.showHorizontalLines", true);
        UIManager.put("Table.showVerticalLines", false);
        UIManager.put("Table.rowHeight", 30);
        UIManager.put("TitlePane.unifiedBackground", true);

        // Apply font tweak only once to avoid cumulative scaling on theme toggle
        if (!fontInitialized) {
            Font f = UIManager.getFont("defaultFont");
            if (f != null) UIManager.put("defaultFont", f.deriveFont(f.getSize2D() + 1f));
            fontInitialized = true;
        }
    }

    @Override
    public void applyLight(JFrame frame) {
        FlatLightLaf.setup();
        commonPolish();
        SwingUtilities.updateComponentTreeUI(frame);
        frame.repaint();
    }

    @Override
    public void applyDark(JFrame frame) {
        FlatDarkLaf.setup();
        commonPolish();
        SwingUtilities.updateComponentTreeUI(frame);
        frame.repaint();
    }
}
