package view.UICreationalPattern.UIBuilders;

import view.UICreationalPattern.UIComponents.CustomTextField;

public class CustomTextFieldBuilder extends UIBuilder {
    @Override
    public CustomTextField build() {
        return new CustomTextField(backgroundColor, hoverBackgroundColor, pressedBackgroundColor, textColor,
                text, size, placeholder, focusPainted, borderPainted, contentAreaFilled, opaque);
    }
}
