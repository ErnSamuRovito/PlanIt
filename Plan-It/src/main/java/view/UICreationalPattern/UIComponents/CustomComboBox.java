package view.UICreationalPattern.UIComponents;

import controller.commandPattern.ActionCommand;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class CustomComboBox<E> extends JComboBox<E> implements UIComponent {
    private final Color backgroundColor;
    private final Color hoverBackgroundColor;
    private final Color selectedBackgroundColor;
    private final Color textColor;
    private final Dimension size;
    private final Boolean editable;
    private final ActionCommand command;
    private String[] items;

    public CustomComboBox(String[] items, Color backgroundColor, Color hoverBackgroundColor,
                          Color selectedBackgroundColor, Color textColor, Dimension size,
                          Boolean editable, ActionCommand command) {

        this.items = items;
        this.backgroundColor = backgroundColor;
        this.hoverBackgroundColor = hoverBackgroundColor;
        this.selectedBackgroundColor = selectedBackgroundColor;
        this.textColor = textColor;
        this.size = size;
        this.editable = editable;
        this.command = command;

        setEditable(editable);
        setPreferredSize(size);
        setBackground(backgroundColor);
        setForeground(textColor);

        // Inizializza il rendering personalizzato
        initializeUI();

        // Eventi specifici
        addListeners();
    }

    private void initializeUI() {
        // Personalizza il renderer per gli elementi del JComboBox
        setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (isSelected) {
                    component.setBackground(selectedBackgroundColor);
                    component.setForeground(Color.WHITE);
                } else {
                    component.setBackground(backgroundColor);
                    component.setForeground(textColor);
                }

                // Cambia il font
                component.setFont(new Font("Arial", Font.PLAIN, 14));
                return component;
            }
        });
    }

    private void addListeners() {
        // Gestisce il passaggio del mouse
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                setBackground(hoverBackgroundColor);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                setBackground(backgroundColor);
            }
        });

        // Gestisce l'evento di selezione di un elemento
        addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED && command != null) {
                    command.execute();
                }
            }
        });
    }

    @Override
    public void initialize() {
        this.items = new String[]{"Option 1", "Option 2", "Option 3"};
    }
}
