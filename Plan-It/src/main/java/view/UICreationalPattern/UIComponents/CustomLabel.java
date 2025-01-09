package view.UICreationalPattern.UIComponents;

import controller.commandPattern.ActionCommand;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CustomLabel extends JLabel implements UIComponent {
    private final Color backgroundColor;
    private final Color hoverBackgroundColor;
    private final Color pressedBackgroundColor;
    private final Color textColor;
    private final Dimension size;
    private final boolean opaque, clickable;
    private final ActionCommand command;

    // Costruttore
    public CustomLabel(Color backgroundColor, Color hoverBackgroundColor, Color pressedBackgroundColor,
                       Color textColor, String text, Dimension size, boolean opaque, boolean clickable,
                       ActionCommand command) {
        super(text); // Imposta il testo iniziale del JLabel

        this.backgroundColor = backgroundColor;
        this.hoverBackgroundColor = hoverBackgroundColor;
        this.pressedBackgroundColor = pressedBackgroundColor;
        this.textColor = textColor;
        this.size = size;
        this.opaque = opaque;
        this.clickable = clickable;
        this.command = command;

        setPreferredSize(size);
        setForeground(textColor);
        setBackground(backgroundColor);
        setOpaque(opaque);
        setHorizontalAlignment(CENTER); // Centra il testo
        setVerticalAlignment(CENTER);
        setAlignmentX(Component.CENTER_ALIGNMENT);

        // Aggiungi i listener per il comportamento del mouse se la label è stata contrassegnata come clickable.
        if (clickable) {
            removeMouseListeners();
            addMouseListeners();
            if (command != null) addActionListener();
            revalidate();
            repaint();
        }
    }

    // Metodo per aggiungere i listener del mouse
    private void addMouseListeners() {
        addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                setForeground(hoverBackgroundColor);
            }
            @Override public void mouseExited(MouseEvent e) {
                setForeground(textColor);
            }
            @Override public void mousePressed(MouseEvent e) {
                setForeground(pressedBackgroundColor);
            }
            @Override public void mouseReleased(MouseEvent e) {
                setForeground(hoverBackgroundColor);
            }
        });
    }

    private void removeMouseListeners() {
        for (MouseListener listener : getMouseListeners()) {
            removeMouseListener(listener);
        }
    }

    private void addActionListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { if (command != null) {command.execute();} }
        });
    }

    @Override public void initialize() {}
}
