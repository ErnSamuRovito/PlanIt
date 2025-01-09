package view.UICreationalPattern.UIComponents;

import javax.swing.*;
import java.awt.*;

public interface UIComponent {
    void initialize();

    default void drawShadow(Graphics2D g2, JComponent component) {
        // Disegna l'ombra sotto il pulsante
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(0, 0, 0, 60)); // Colore nero semi-trasparente
        g2.fillRoundRect(2, 4, component.getWidth() - 4, component.getHeight() - 4, 15, 15);

        // Disegna il rettangolo del pulsante sopra l’ombra
        g2.setColor(component.getBackground()); // Usa il colore di sfondo attuale
        g2.fillRoundRect(0, 0, component.getWidth() - 4, component.getHeight() - 4, 15, 15);
    }

    default void drawPlaceholder(Graphics2D g2, JComponent component, Boolean isPlaceholderVisible,
                                 String placeholder, int xOffset) {
        if (isPlaceholderVisible && placeholder != null) { // Verifica se il placeholder non è null
            g2.setColor(Color.DARK_GRAY);
            FontMetrics metrics = g2.getFontMetrics();
            int yOffset = (component.getHeight() + metrics.getAscent()) / 2 - metrics.getDescent();  // Posiziona il testo verticalmente al centro
            g2.drawString(placeholder, xOffset, yOffset);
        }
    }

}
