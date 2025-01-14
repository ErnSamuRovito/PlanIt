package model.factories;

import view.UICreationalPattern.UIBuilders.CustomTextFieldBuilder;
import view.UICreationalPattern.UIBuilders.UIBuilder;
import view.UICreationalPattern.UIBuilders.UIDirector;
import view.UICreationalPattern.UIComponents.UIComponent;

public class TextFieldFactory extends UiFactory {
    @Override
    public UIBuilder createBuild() {
        UIBuilder builder = new CustomTextFieldBuilder();
        UIDirector.buildStandardTextField(builder);
        return builder;
    }
}
