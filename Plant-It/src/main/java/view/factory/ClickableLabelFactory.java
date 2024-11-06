package view.factory;

import view.UI.CustomClickableLabel;

import java.awt.*;
import java.awt.event.MouseListener;

public class ClickableLabelFactory implements UIComponentFactory<CustomClickableLabel>{
    private final String text;
    private Dimension size;
    private MouseListener mouseListener;
    private Color labelColor;

    public ClickableLabelFactory(String text)
    {
        this.text = text;
    }

    public ClickableLabelFactory setSize(Dimension size) {
        this.size = size;
        return this;
    }

    public ClickableLabelFactory setLabelColor(Color labelColor) {
        this.labelColor = labelColor;
        return this;
    }

    public ClickableLabelFactory setMouseListener(MouseListener mouseListener) {
        this.mouseListener = mouseListener;
        return this;
    }

    @Override
    public CustomClickableLabel create() {
        CustomClickableLabel clickableLabel = new CustomClickableLabel(text);
        if (size != null) clickableLabel.setPreferredSize(size);
        if (mouseListener != null) clickableLabel.addMouseListener(mouseListener);
        if (labelColor != null) clickableLabel.setForeground(labelColor);
        return clickableLabel;
    }
}
