package view.UICreationalPattern.UIBuilders;

import view.UICreationalPattern.UIComponents.CustomPasswordField;

public class CustomPasswordFieldBuilder extends UIBuilder {
    @Override
    public CustomPasswordField build() {
        return new CustomPasswordField(backgroundColor, hoverBackgroundColor, pressedBackgroundColor, textColor,
                text, size, placeholder, focusPainted, borderPainted, contentAreaFilled, opaque);
    }
}
