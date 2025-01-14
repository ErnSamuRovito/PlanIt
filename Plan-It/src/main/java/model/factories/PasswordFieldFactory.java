package model.factories;

import view.UICreationalPattern.UIBuilders.CustomPasswordFieldBuilder;
import view.UICreationalPattern.UIBuilders.UIBuilder;
import view.UICreationalPattern.UIBuilders.UIDirector;
import view.UICreationalPattern.UIComponents.UIComponent;

public class PasswordFieldFactory extends UiFactory{
    @Override
    public UIBuilder createBuild() {
        UIBuilder passwordFieldBuilder = new CustomPasswordFieldBuilder();
        UIDirector.buildStandardPasswordField(passwordFieldBuilder);
        return passwordFieldBuilder;
    }
}
