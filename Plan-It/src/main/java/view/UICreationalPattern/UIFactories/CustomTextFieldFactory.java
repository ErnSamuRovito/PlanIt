package view.UICreationalPattern.UIFactories;


import view.UICreationalPattern.UIBuilders.UIBuilder;
import view.UICreationalPattern.UIComponents.UIComponent;

public class CustomTextFieldFactory extends UIComponentFactory {
    private UIBuilder builder;

    public CustomTextFieldFactory(UIBuilder builder) {
        this.builder = builder;
    }

    @Override
    public UIComponent createComponent() {
        return builder.build();
    }
}