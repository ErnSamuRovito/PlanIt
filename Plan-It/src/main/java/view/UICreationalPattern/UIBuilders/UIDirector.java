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
                .opaque(true);
    }

    public static void buildStandardTextField(UIBuilder builder) {
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
    public static void buildStandardPasswordField(UIBuilder builder) {
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
    public static void buildStandardClickableLabel(UIBuilder builder) {
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

    public static void buildStandardLabel(UIBuilder builder) {
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

    public static void buildBackClickableLabel(UIBuilder builder) {
        builder .backgroundColor(GlobalResources.COLOR_PANNA)
                .hoverBackgroundColor(GlobalResources.COLOR_GREEN_1)
                .pressedBackgroundColor(GlobalResources.COLOR_GREEN_2)
                .textColor(GlobalResources.COLOR_PANNA)
                .focusPainted(false)
                .borderPainted(false)
                .contentAreaFilled(false)
                .opaque(false)
                .clickable(true);
    }

    public static void buildStandardTextPane(UIBuilder builder) {
        builder .backgroundColor(Color.WHITE)
                .textColor(Color.BLACK)
                .size(new Dimension(500, 200))
                .contentType("text/html")
                .content("""
                        <html>
                            <h1>Ciao, questo è un task, completalo!</h1>
                            <h2>prova prova prova prova prova prova</h2>
                            <h3>prova prova prova prova prova prova</h3>
                            <h1>Ciao, questo è un task, completalo!</h1>
                            <h2>prova prova prova prova prova prova</h2>
                            <h3>prova prova prova prova prova prova</h3>
                        </html>
                    """)
                .editable(false);
    }
}
