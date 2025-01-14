package model.factories;

import view.UICreationalPattern.UIBuilders.UIBuilder;

public abstract class UiFactory {
    public abstract UIBuilder createBuild();
}
