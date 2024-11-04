// Package: factory

package view.factory;

import view.CustomButton;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ButtonFactory implements UIComponentFactory<CustomButton> {

    private String text;
    private Dimension size;
    private ActionListener actionListener;

    // Costruttore per bottoni semplici
    public ButtonFactory(String text) {
        this.text = text;
    }

    // Metodo per impostare la dimensione opzionale
    public ButtonFactory setSize(Dimension size) {
        this.size = size;
        return this;
    }

    // Metodo per impostare l'action listener opzionale
    public ButtonFactory setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
        return this;
    }

    @Override
    public CustomButton create() {
        CustomButton button = new CustomButton(text);
        if (size != null) button.setPreferredSize(size);
        if (actionListener != null) button.addActionListener(actionListener);
        return button;
    }
}
