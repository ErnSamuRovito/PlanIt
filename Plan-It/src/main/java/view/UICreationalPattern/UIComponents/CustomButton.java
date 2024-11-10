package view.UICreationalPattern.UIComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomButton extends JButton implements UIComponent {
    private final Color backgroundColor;
    private final Color hoverBackgroundColor;
    private final Color pressedBackgroundColor;
    private final Color textColor;
    private final String text;
    private final Dimension size;
    private final String placeholder;

    private final Boolean focusPainted;
    private final Boolean borderPainted;
    private final Boolean contentAreaFilled;
    private final Boolean opaque;

    public CustomButton(Color backgroundColor, Color hoverBackgroundColor, Color pressedBackgroundColor, Color textColor,
                        String text, Dimension size, String placeholder,
                        Boolean focusPainted, Boolean borderPainted, Boolean contentAreaFilled, Boolean opaque) {
        super(text);

        this.backgroundColor = backgroundColor;
        this.hoverBackgroundColor = hoverBackgroundColor;
        this.pressedBackgroundColor = pressedBackgroundColor;
        this.textColor = textColor;
        this.text = text;
        this.size = size;
        this.placeholder = placeholder;
        this.focusPainted = focusPainted;
        this.borderPainted = borderPainted;
        this.contentAreaFilled = contentAreaFilled;
        this.opaque = opaque;

        setPreferredSize(size);
        setBackground(backgroundColor);
        setForeground(textColor);
        setOpaque(opaque);
        setFocusPainted(focusPainted);
        setBorderPainted(borderPainted);
        setContentAreaFilled(contentAreaFilled);
    }

    @Override
    public void initialize() {
        //qui è possibile personalizzare ulteriormente il bottone...
        // Gestisci eventi di hover e click
        addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { setBackground(hoverBackgroundColor); }
            @Override public void mouseExited(MouseEvent e) { setBackground(backgroundColor); }
            @Override public void mousePressed(MouseEvent e) { setBackground(pressedBackgroundColor); }
            @Override public void mouseReleased(MouseEvent e) { setBackground(hoverBackgroundColor); }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        drawShadow(g2,this);
        super.paintComponent(g2);
        g2.dispose();
    }
}