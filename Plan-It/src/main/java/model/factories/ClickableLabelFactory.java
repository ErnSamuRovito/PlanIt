package model.factories;

import view.UICreationalPattern.UIBuilders.CustomLabelBuilder;
import view.UICreationalPattern.UIBuilders.UIBuilder;
import view.UICreationalPattern.UIBuilders.UIDirector;

public class ClickableLabelFactory extends UiFactory {
    @Override
    public UIBuilder createBuild() {
        UIBuilder labelBuilder = new CustomLabelBuilder();
        UIDirector.buildStandardClickableLabel(labelBuilder);
        return labelBuilder;
    }
}
