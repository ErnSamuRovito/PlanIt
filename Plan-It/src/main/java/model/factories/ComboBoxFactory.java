package model.factories;

import view.UICreationalPattern.UIBuilders.CustomComboBoxBuilder;
import view.UICreationalPattern.UIBuilders.UIBuilder;
import view.UICreationalPattern.UIBuilders.UIDirector;

public class ComboBoxFactory extends UiFactory {
    @Override
    public UIBuilder createBuild() {
        // Usa il builder per CustomComboBox
        UIBuilder builder = new CustomComboBoxBuilder();
        // Configura il ComboBox usando il direttore
        UIDirector.buildStandardComboBox(builder);
        return builder;
    }
}
