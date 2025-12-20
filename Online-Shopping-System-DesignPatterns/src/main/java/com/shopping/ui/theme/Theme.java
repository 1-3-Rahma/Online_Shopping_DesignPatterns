package com.shopping.ui.theme;

import javax.swing.*;

/**
 * Bridge Abstraction: Theme.
 */
public interface Theme {
    String name();
    void apply(JFrame frame);
}
