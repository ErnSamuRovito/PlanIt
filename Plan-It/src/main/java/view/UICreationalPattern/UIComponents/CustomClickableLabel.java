package view.UICreationalPattern.UIComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomClickableLabel extends JLabel implements UIComponent {
    private final Color backgroundColor;
    private final Color hoverBackgroundColor;
    private final Color pressedBackgroundColor;
    private final Color textColor;
    private final String placeholder;
    private final Dimension size;
    private final boolean focusPainted;
    private final boolean borderPainted;
    private final boolean contentAreaFilled;
    private final boolean opaque;

    // Costruttore
    public CustomClickableLabel(Color backgroundColor, Color hoverBackgroundColor, Color pressedBackgroundColor,
                                Color textColor, String text, Dimension size, String placeholder,
                                boolean focusPainted, boolean borderPainted, boolean contentAreaFilled, boolean opaque) {
        super(text); // Imposta il testo iniziale del JLabel

        this.backgroundColor = backgroundColor;
        this.hoverBackgroundColor = hoverBackgroundColor;
        this.pressedBackgroundColor = pressedBackgroundColor;
        this.textColor = textColor;
        this.placeholder = placeholder;
        this.size = size;
        this.focusPainted = focusPainted;
        this.borderPainted = borderPainted;
        this.contentAreaFilled = contentAreaFilled;
        this.opaque = opaque;

        // Configura il JLabel
        setPreferredSize(size);
        setForeground(textColor);
        setBackground(backgroundColor);
        setOpaque(opaque);
        setHorizontalAlignment(CENTER); // Centra il testo
        setVerticalAlignment(CENTER);

        // Aggiungi i listener per il comportamento del mouse
        addMouseListeners();
    }

    // Metodo per aggiungere i listener del mouse
    private void addMouseListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setForeground(hoverBackgroundColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setForeground(backgroundColor);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setForeground(pressedBackgroundColor);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setForeground(hoverBackgroundColor);
            }
        });
    }

    @Override
    public void initialize() {}
}
