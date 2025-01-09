package view.UICreationalPattern.UIBuilders;
import view.UICreationalPattern.UIComponents.CustomTextPane;

public class CustomTextPaneBuilder extends UIBuilder {
    @Override
    public CustomTextPane build() {
        return new CustomTextPane(backgroundColor, textColor, size, contentType, content, editable);
    }
}
