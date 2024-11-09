package view.UICreationalPattern.UIBuilders;

import view.UICreationalPattern.UIComponents.CustomClickableLabel;

public class CustomClickableLabelBuilder extends UIBuilder {
    @Override
    public CustomClickableLabel build() {
        return new CustomClickableLabel(backgroundColor, hoverBackgroundColor, pressedBackgroundColor, textColor,
                text, size, placeholder, focusPainted, borderPainted, contentAreaFilled, opaque);
    }
}
