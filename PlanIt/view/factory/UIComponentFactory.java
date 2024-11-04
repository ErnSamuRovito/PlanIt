// Package: factory

package view.factory;

import javax.swing.*;

public interface UIComponentFactory<T extends JComponent> {
    T create();
}
