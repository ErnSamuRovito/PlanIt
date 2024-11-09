package view.UICreationalPattern.UIFactories;

import view.UICreationalPattern.UIBuilders.UIBuilder;
import view.UICreationalPattern.UIComponents.UIComponent;

public abstract class UIComponentFactory {
    public UIComponent orderComponent(UIBuilder builder){
        UIComponent component=createComponent();
        component.initialize();
        return component;
    }

    public abstract UIComponent createComponent();
}
