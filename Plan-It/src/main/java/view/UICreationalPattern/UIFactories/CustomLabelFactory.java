package view.UICreationalPattern.UIFactories;


import view.UICreationalPattern.UIBuilders.UIBuilder;
import view.UICreationalPattern.UIComponents.UIComponent;

public class CustomLabelFactory extends UIComponentFactory {
    private UIBuilder builder;

    public CustomLabelFactory(UIBuilder builder) {
        this.builder = builder;
    }

    @Override
    public UIComponent createComponent() {
        return builder.build();
    }
}