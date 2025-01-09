package view.UICreationalPattern.UIFactories;

import view.UICreationalPattern.UIBuilders.UIBuilder;
import view.UICreationalPattern.UIComponents.UIComponent;

public class CustomTextPaneFactory extends UIComponentFactory {
    private UIBuilder builder;

    public CustomTextPaneFactory(UIBuilder builder) {
        this.builder = builder;
    }

    @Override
    public UIComponent createComponent() {
        return builder.build();
    }
}
