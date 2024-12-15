package view.UICreationalPattern.UIComponents;

import jdk.jfr.ContentType;

import javax.swing.*;
import java.awt.*;

public class CustomTextPane extends JTextPane implements UIComponent {
    private final Color backgroundColor;
    private final Color textColor;
    private final Dimension size;
    private final String contentType;
    private final String content;
    private final Boolean editable;

    public CustomTextPane(Color backgroundColor, Color textColor, Dimension size, String contentType, String content, Boolean editable) {
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.size = size;
        this.contentType = contentType;
        this.content = content;
        this.editable = editable;

        setPreferredSize(size);
        setBackground(backgroundColor);
        setForeground(textColor);
        if (contentType!=null){
            setContentType(contentType);
        }
        setText(content);
        if (editable==null)
            setEditable(false);
        else setEditable(editable);
    }

    @Override
    public void initialize() {
        // Possibilità di personalizzare ulteriormente il comportamento del JTextPane, se necessario
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Qui possiamo aggiungere logica di personalizzazione della grafica (ombra, bordi, ecc.)
    }
}
