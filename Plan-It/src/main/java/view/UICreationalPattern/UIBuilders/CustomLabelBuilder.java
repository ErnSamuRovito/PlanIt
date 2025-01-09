package view.UICreationalPattern.UIBuilders;

import view.UICreationalPattern.UIComponents.CustomLabel;

public class CustomLabelBuilder extends UIBuilder {
    @Override
    public CustomLabel build() {
        return new CustomLabel(
                backgroundColor, hoverBackgroundColor, pressedBackgroundColor, textColor, text, size, opaque, clickable, action
        );
    }
}
