package view.UICreationalPattern.UIBuilders;

import controller.ToLoginController;
import controller.ToSigninController;
import core.GlobalResources;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UIDirector {
    public void buildStandardButton(UIBuilder builder) {
        builder .backgroundColor(GlobalResources.COLOR_GREEN_1)
                .hoverBackgroundColor(GlobalResources.COLOR_GREEN_2)
                .pressedBackgroundColor(GlobalResources.COLOR_GREEN_1)
                .textColor(Color.WHITE)
                .focusPainted(false)
                .borderPainted(false)
                .contentAreaFilled(false)
                .opaque(true);
    }

    public void buildStandardTextField(UIBuilder builder) {
        builder .backgroundColor(GlobalResources.COLOR_CREMA)
                .hoverBackgroundColor(GlobalResources.COLOR_WHITE)
                .pressedBackgroundColor(GlobalResources.COLOR_GREEN_2)
                .textColor(Color.BLACK)
                .focusPainted(false)
                .borderPainted(false)
                .contentAreaFilled(false)
                .opaque(false);
    }

    // Crea un campo di password standard
    public void buildStandardPasswordField(UIBuilder builder) {
        builder .backgroundColor(GlobalResources.COLOR_CREMA)
                .hoverBackgroundColor(GlobalResources.COLOR_WHITE)
                .pressedBackgroundColor(GlobalResources.COLOR_GREEN_2)
                .textColor(Color.BLACK)
                .focusPainted(false)
                .borderPainted(false)
                .contentAreaFilled(false)
                .opaque(false);
    }

    // Crea una clickable label standard
    public void buildStandardClickableLabel(UIBuilder builder) {
        builder .backgroundColor(GlobalResources.COLOR_CREMA)
                .hoverBackgroundColor(GlobalResources.COLOR_GREEN_1)
                .pressedBackgroundColor(GlobalResources.COLOR_GREEN_2)
                .textColor(GlobalResources.COLOR_CREMA)
                .focusPainted(false)
                .borderPainted(false)
                .contentAreaFilled(false)
                .opaque(false)
                .clickable(true);
    }

    public void buildStandardLabel(UIBuilder builder) {
        builder .backgroundColor(GlobalResources.COLOR_CREMA)
                .hoverBackgroundColor(GlobalResources.COLOR_GREEN_1)
                .pressedBackgroundColor(GlobalResources.COLOR_GREEN_2)
                .textColor(GlobalResources.COLOR_CREMA)
                .focusPainted(false)
                .borderPainted(false)
                .contentAreaFilled(false)
                .opaque(false)
                .clickable(false);
    }
}
