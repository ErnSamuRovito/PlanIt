package view.factory;

import view.CustomTextField;
import java.awt.*;

public class TextFieldFactory implements UIComponentFactory<CustomTextField>
{
    private Dimension size;
    private boolean isPassword;

    public TextFieldFactory() {}

    public TextFieldFactory setSize(Dimension size)
    {
        this.size = size;
        return this;
    }

    public TextFieldFactory setIsPassword(boolean isPassword)
    {
        this.isPassword = isPassword;
        return this;
    }

    @Override
    public CustomTextField create()
    {
        CustomTextField textField = new CustomTextField(isPassword);
        if (size != null) textField.setPreferredSize(size);
        return textField;
    }
}