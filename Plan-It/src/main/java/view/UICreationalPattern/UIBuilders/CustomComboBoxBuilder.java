package view.UICreationalPattern.UIBuilders;

import view.UICreationalPattern.UIComponents.CustomComboBox;
import view.UICreationalPattern.UIComponents.UIComponent;

import java.awt.*;

public class CustomComboBoxBuilder extends UIBuilder {
    private String[] items ; // Elementi della combo box

    @Override
    public UIComponent build() {
        if (items == null) {
            throw new IllegalStateException("Items cannot be null for CustomComboBox.");
        }

        return new CustomComboBox<>(
                items,
                backgroundColor != null ? backgroundColor : Color.WHITE,
                hoverBackgroundColor != null ? hoverBackgroundColor : Color.LIGHT_GRAY,
                pressedBackgroundColor != null ? pressedBackgroundColor : Color.GRAY,
                textColor != null ? textColor : Color.BLACK,
                size != null ? size : new Dimension(150, 30),
                editable != null ? editable : false,
                action
        );
    }
}
