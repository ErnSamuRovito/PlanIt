package view.UICreationalPattern.UIBuilders;
import view.UICreationalPattern.UIComponents.CustomButton;

public class CustomButtonBuilder extends UIBuilder {
    @Override
    public CustomButton build() {
        return new CustomButton(
                backgroundColor, hoverBackgroundColor, pressedBackgroundColor, textColor, text, size, opaque, action
        );
    }
}
