package model.factories;

import view.UICreationalPattern.UIBuilders.CustomButtonBuilder;
import view.UICreationalPattern.UIBuilders.UIBuilder;
import view.UICreationalPattern.UIBuilders.UIDirector;
import view.UICreationalPattern.UIComponents.UIComponent;

public class ButtonFactory extends UiFactory {
    @Override
    public UIBuilder createBuild() {
        UIBuilder builder = new CustomButtonBuilder();
        UIDirector.buildStandardButton(builder);
        return builder;
    }
}
