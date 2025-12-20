package com.shopping.ui.theme;

import javax.swing.*;

public class DarkTheme implements Theme {
    private final ThemeApplier applier;

    public DarkTheme(ThemeApplier applier) { this.applier = applier; }

    @Override public String name() { return "Dark"; }
    @Override public void apply(JFrame frame) { applier.applyDark(frame); }
}
