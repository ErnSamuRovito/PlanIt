package view.UICreationalPattern.UIBuilders;

import core.GlobalResources;
import java.awt.*;

public class UIDirector {
    public static void buildStandardButton(UIBuilder builder) {
        builder .backgroundColor(GlobalResources.COLOR_GREEN_1)
                .hoverBackgroundColor(GlobalResources.COLOR_GREEN_2)
                .pressedBackgroundColor(GlobalResources.COLOR_GREEN_1)
                .textColor(Color.WHITE)
                .focusPainted(false)
                .borderPainted(false)
                .contentAreaFilled(false)
                .opaque(true)
                .size(GlobalResources.BUTTON_SIZE);
    }

    public static void buildStandardTextField(UIBuilder builder) {
        builder .backgroundColor(GlobalResources.COLOR_CREMA)
                .hoverBackgroundColor(GlobalResources.COLOR_WHITE)
                .pressedBackgroundColor(GlobalResources.COLOR_GREEN_2)
                .textColor(Color.BLACK)
                .focusPainted(false)
                .borderPainted(false)
                .contentAreaFilled(false)
                .opaque(false)
                .size(GlobalResources.FIELD_SIZE);
    }

    // Crea un campo di password standard
    public static void buildStandardPasswordField(UIBuilder builder) {
        builder .backgroundColor(GlobalResources.COLOR_CREMA)
                .hoverBackgroundColor(GlobalResources.COLOR_WHITE)
                .pressedBackgroundColor(GlobalResources.COLOR_GREEN_2)
                .textColor(Color.BLACK)
                .focusPainted(false)
                .borderPainted(false)
                .contentAreaFilled(false)
                .opaque(false)
                .size(GlobalResources.FIELD_SIZE);
    }

    // Crea una clickable label standard
    public static void buildStandardClickableLabel(UIBuilder builder) {
        builder .backgroundColor(GlobalResources.COLOR_GREEN_2)
                .hoverBackgroundColor(GlobalResources.COLOR_GREEN_1)
                .pressedBackgroundColor(GlobalResources.COLOR_GREEN_2)
                .textColor(GlobalResources.COLOR_GREEN_2)
                .focusPainted(false)
                .borderPainted(false)
                .contentAreaFilled(false)
                .opaque(false)
                .clickable(true)
                .size(GlobalResources.LABEL_SIZE);
    }

    public static void buildStandardLabel(UIBuilder builder) {
        builder .backgroundColor(GlobalResources.COLOR_CREMA)
                .hoverBackgroundColor(GlobalResources.COLOR_GREEN_1)
                .pressedBackgroundColor(GlobalResources.COLOR_GREEN_2)
                .textColor(GlobalResources.COLOR_CREMA)
                .focusPainted(false)
                .borderPainted(false)
                .contentAreaFilled(false)
                .opaque(false)
                .clickable(false)
                .size(GlobalResources.LABEL_SIZE);
    }

    public static void buildBackClickableLabel(UIBuilder builder) {
        builder .backgroundColor(GlobalResources.COLOR_GREEN_2)
                .hoverBackgroundColor(GlobalResources.COLOR_GREEN_1)
                .pressedBackgroundColor(GlobalResources.COLOR_GREEN_2)
                .textColor(GlobalResources.COLOR_GREEN_2)
                .focusPainted(false)
                .borderPainted(false)
                .contentAreaFilled(false)
                .opaque(false)
                .clickable(true)
                .text("Back")
                .size(GlobalResources.LABEL_SIZE);
    }

    public static void buildStandardTextPane(UIBuilder builder) {
        builder .backgroundColor(Color.WHITE)
                .textColor(Color.BLACK)
                .size(new Dimension(500, 200))
                .contentType("text/html")
                .editable(false);
    }

    // Crea un ComboBox standard
    public static void buildStandardComboBox(UIBuilder builder) {
        builder .backgroundColor(GlobalResources.COLOR_CREMA)
                .hoverBackgroundColor(GlobalResources.COLOR_GREEN_1)
                .pressedBackgroundColor(GlobalResources.COLOR_GREEN_2)
                .textColor(Color.BLACK)
                .size(GlobalResources.FIELD_SIZE)
                .editable(false); // ComboBox di default non editabile
    }

    public static void buildStandardDatePicker(UIBuilder builder) {
        builder.backgroundColor(GlobalResources.COLOR_CREMA)
                .hoverBackgroundColor(GlobalResources.COLOR_GREEN_1)
                .pressedBackgroundColor(GlobalResources.COLOR_GREEN_2)
                .textColor(Color.BLACK)
                .focusPainted(false)
                .borderPainted(true)
                .contentAreaFilled(true)
                .opaque(true)
                .size(GlobalResources.FIELD_SIZE)
                .editable(true); // If the date picker supports text editing
    }
}
