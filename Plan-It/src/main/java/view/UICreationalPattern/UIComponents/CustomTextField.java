package view.UICreationalPattern.UIComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomTextField extends JTextField implements UIComponent {
    private final Color backgroundColor,hoverBackgroundColor,pressedBackgroundColor,textColor;
    private final String text,placeholder;
    private final Dimension size;
    private final Boolean focusPainted,borderPainted,contentAreaFilled,opaque;
    private final int offset=5;

    private boolean isPlaceholderVisible = true;

    public CustomTextField(Color backgroundColor, Color hoverBackgroundColor, Color pressedBackgroundColor, Color textColor,
                           String text, Dimension size, String placeholder,
                           Boolean focusPainted, Boolean borderPainted, Boolean contentAreaFilled, Boolean opaque) {
        super();

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
        this.opaque = (opaque != null) ? opaque : true;  // Se opaque è null, impostalo su true

        setText(this.text);
        setPreferredSize(size);
        setBackground(backgroundColor);
        setForeground(textColor);
        setOpaque(this.opaque);  // Usa il valore di opaque
        setBorder(BorderFactory.createEmptyBorder());

        // Aggiungi il listener del focus
        addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                // Cambia il colore di sfondo quando il campo ottiene il focus
                setBackground(focusPainted ? hoverBackgroundColor : backgroundColor);
                // Nascondi il placeholder quando il campo riceve il focus
                isPlaceholderVisible = false;
                repaint();
            }

            @Override public void focusLost(FocusEvent e) {
                // Ripristina il colore di sfondo quando il campo perde il focus
                setBackground(backgroundColor);
                // Mostra il placeholder se il campo è vuoto quando perde il focus
                if (getText().isEmpty()) {
                    isPlaceholderVisible = true;
                }
                repaint();
            }
        });

        // Gestisci gli eventi di mouse hover
        addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { setBackground(hoverBackgroundColor); }
            @Override public void mouseExited(MouseEvent e) { setBackground(backgroundColor); }
            @Override public void mousePressed(MouseEvent e) { setBackground(pressedBackgroundColor); }
            @Override public void mouseReleased(MouseEvent e) { setBackground(hoverBackgroundColor); }
        });
    }

    @Override public void initialize() {
        //eventuali personalizzazioni aggiuntive
    }

    @Override public Insets getInsets() {
        return new Insets(0,offset,0,offset); //(top, left, bottom, right)
    }

    @Override protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        drawShadow(g2,this);
        drawPlaceholder(g2,this,isPlaceholderVisible,placeholder,offset);
        super.paintComponent(g2);
        g2.dispose();
    }
}