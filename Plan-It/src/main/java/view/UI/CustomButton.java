package view.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import core.GlobalResources;

public class CustomButton extends JButton {
    private Color backgroundColor;
    private Color hoverBackgroundColor;
    private Color pressedBackgroundColor;
    private Color textColor;

    public CustomButton(String text) {
        super(text);
        setFont(new Font("SansSerif", Font.BOLD, 14));

        // Colori di default presi da GlobalResources
        this.backgroundColor = GlobalResources.COLOR_GREEN_1;
        this.hoverBackgroundColor = GlobalResources.COLOR_GREEN_2;
        this.pressedBackgroundColor = GlobalResources.COLOR_GREEN_1;
        this.textColor = GlobalResources.COLOR_WHITE;

        setForeground(textColor);
        setFocusPainted(false);
        setBorderPainted(false);

        // Imposta per permettere il disegno personalizzato
        setContentAreaFilled(false);
        setOpaque(false);

        // Imposta il colore di sfondo iniziale
        setBackground(backgroundColor);

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

        // Disegna l'ombra sotto il pulsante
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(0, 0, 0, 60)); // Colore nero semi-trasparente
        g2.fillRoundRect(2, 4, getWidth() - 4, getHeight() - 4, 15, 15);

        // Disegna il rettangolo del pulsante sopra l’ombra
        g2.setColor(getBackground()); // Usa il colore di sfondo attuale
        g2.fillRoundRect(0, 0, getWidth() - 4, getHeight() - 4, 15, 15);

        // Disegna il testo sopra il disegno
        super.paintComponent(g2);

        g2.dispose();
    }
}
