package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import core.GlobalResources;

public class CustomTextField extends JTextField {
    private Color backgroundColor;
    private Color focusBackgroundColor;
    private Color textColor;
    private char echoChar;
    private boolean isPasswordField;

    public CustomTextField(boolean isPassword) {
        setFont(new Font("SansSerif", Font.PLAIN, 14));

        this.backgroundColor = GlobalResources.COLOR_WHITE;
        this.focusBackgroundColor = GlobalResources.COLOR_GREEN_1;
        this.textColor = Color.BLACK;

        setForeground(textColor);
        setBackground(backgroundColor);
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        this.isPasswordField = isPassword;
        setEchoChar(isPassword ? '*' : (char) 0);

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                setBackground(focusBackgroundColor);
            }

            @Override
            public void focusLost(FocusEvent e) {
                setBackground(backgroundColor);
            }
        });

        setOpaque(false);
    }

    public void setEchoChar(char c) {
        this.echoChar = c;
    }

    @Override
    public String getText() {
        return super.getText();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the background with a shadow effect
        g2.setColor(new Color(0, 0, 0, 60));
        g2.fillRoundRect(2, 4, getWidth() - 4, getHeight() - 4, 15, 15);

        // Fill the rounded rectangle with the background color
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - 4, getHeight() - 4, 15, 15);

        if (isPasswordField) {
            String echoedText = createRepeatedString(echoChar, getText().length());
            g2.setColor(getForeground());
            g2.drawString(echoedText, 10, getHeight() - 10); // Adjust y position as needed
        } else {
            super.paintComponent(g2);
        }

        g2.dispose();
    }

    private String createRepeatedString(char c, int count) {
        StringBuilder sb = new StringBuilder(count);
        for (int i = 0; i < count; i++) {
            sb.append(c);
        }
        return sb.toString();
    }
}
