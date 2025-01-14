package view.UICreationalPattern.UIBuilders;

import view.UICreationalPattern.UIComponents.CustomDatePicker;
import view.UICreationalPattern.UIComponents.UIComponent;

public class CustomDataPickerBuilder extends UIBuilder{
    @Override
    public UIComponent build() {
        return new CustomDatePicker(
                backgroundColor,
                textColor,
                size,
                editable,
                date);
    }
}
