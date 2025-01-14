package view.UICreationalPattern.UIBuilders;

import controller.commandPattern.ActionCommand;
import jdk.jfr.ContentType;
import view.UICreationalPattern.UIComponents.UIComponent;
import java.awt.*;

public abstract class UIBuilder {
    protected Color backgroundColor;
    protected Color hoverBackgroundColor;
    protected Color pressedBackgroundColor;
    protected Color textColor;
    protected String text;
    protected Dimension size;
    protected String placeholder;
    protected Boolean focusPainted;
    protected Boolean borderPainted;
    protected Boolean contentAreaFilled;
    protected Boolean opaque;
    protected Boolean clickable;
    protected ActionCommand action;
    protected String contentType;
    protected String content;
    protected Boolean editable;
    protected String date;
    protected int selectedIndex;

    public UIBuilder text(String text) {
        this.text = text;
        return this;
    }
    public UIBuilder size(Dimension size) {
        this.size = size;
        return this;
    }
    public UIBuilder placeholder(String placeholder) {
        this.placeholder = placeholder;
        return this;
    }
    public UIBuilder backgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }
    public UIBuilder hoverBackgroundColor(Color hoverBackgroundColor) {
        this.hoverBackgroundColor = hoverBackgroundColor;
        return this;
    }
    public UIBuilder pressedBackgroundColor(Color pressedBackgroundColor) {
        this.pressedBackgroundColor = pressedBackgroundColor;
        return this;
    }
    public UIBuilder textColor(Color textColor) {
        this.textColor = textColor;
        return this;
    }
    public UIBuilder focusPainted(Boolean focusPainted) {
        this.focusPainted = focusPainted;
        return this;
    }
    public UIBuilder borderPainted(Boolean borderPainted) {
        this.borderPainted = borderPainted;
        return this;
    }
    public UIBuilder contentAreaFilled(Boolean contentAreaFilled) {
        this.contentAreaFilled = contentAreaFilled;
        return this;
    }
    public UIBuilder opaque(Boolean opaque) {
        this.opaque = opaque;
        return this;
    }
    public UIBuilder clickable(Boolean clickable) {
        this.clickable = clickable;
        return this;
    }
    public UIBuilder action(ActionCommand action) {
        this.action = action;
        return this;
    }
    public UIBuilder contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }
    public UIBuilder content(String content) {
        this.content = content;
        return this;
    }
    public UIBuilder editable(Boolean editable) {
        this.editable = editable;
        return this;
    }
    public UIBuilder date(String date) {
        this.date = date;
        return this;
    }
    public UIBuilder selectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
        return this;
    }

    // Metodo astratto che verrà implementato nelle classi concrete
    public abstract UIComponent build();
}
