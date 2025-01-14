package model.factories;

import view.UICreationalPattern.UIBuilders.CustomTextPaneBuilder;
import view.UICreationalPattern.UIBuilders.UIBuilder;
import view.UICreationalPattern.UIBuilders.UIDirector;

public class TextPaneFactory extends UiFactory {
    @Override
    public UIBuilder createBuild() {
        UIBuilder builder = new CustomTextPaneBuilder();
        UIDirector.buildStandardTextPane(builder);
        return builder;
    }
}
