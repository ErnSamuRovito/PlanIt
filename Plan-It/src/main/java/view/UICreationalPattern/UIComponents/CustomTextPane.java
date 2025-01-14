package view.UICreationalPattern.UIComponents;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.html.HTMLEditorKit;
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

        if (contentType != null) {
            setContentType(contentType);
            if ("text/html".equals(contentType)) {
                setEditorKit(new HTMLEditorKit());  // Usa HTMLEditorKit per il contenuto HTML
            }
        }

        setText(content);

        if (editable == null)
            setEditable(false);
        else
            setEditable(editable);
    }

    @Override
    public void initialize() {
        // Eventuali personalizzazioni aggiuntive
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Eventuali personalizzazioni grafiche
    }
}
