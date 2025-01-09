package view.UICreationalPattern.UIFactories;

import view.UICreationalPattern.UIBuilders.UIBuilder;
import view.UICreationalPattern.UIComponents.UIComponent;

public class CustomPasswordFieldFactory extends UIComponentFactory {
    private UIBuilder builder;

    public CustomPasswordFieldFactory(UIBuilder builder) {
        this.builder = builder;
    }

    @Override
    public UIComponent createComponent() {
        return builder.build();
    }
}