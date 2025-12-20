package com.shopping.ui.theme;

import javax.swing.*;

public class LightTheme implements Theme {
    private final ThemeApplier applier;

    public LightTheme(ThemeApplier applier) { this.applier = applier; }

    @Override public String name() { return "Light"; }
    @Override public void apply(JFrame frame) { applier.applyLight(frame); }
}
