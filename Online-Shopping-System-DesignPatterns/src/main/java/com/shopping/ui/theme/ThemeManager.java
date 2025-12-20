package com.shopping.ui.theme;

import javax.swing.*;

/**
 * Manager for current theme.
 */
public class ThemeManager {
    private static final ThemeManager INSTANCE = new ThemeManager();

    private final ThemeApplier applier = new FlatLafThemeApplier();
    private Theme current = new LightTheme(applier);

    private ThemeManager() {}

    public static ThemeManager getInstance() { return INSTANCE; }

    public boolean isDark() { return "Dark".equalsIgnoreCase(current.name()); }

    public void setDark(boolean dark, JFrame frame) {
        current = dark ? new DarkTheme(applier) : new LightTheme(applier);
        if (frame != null) current.apply(frame);
    }

    public void apply(JFrame frame) {
        if (frame != null) current.apply(frame);
    }
}
