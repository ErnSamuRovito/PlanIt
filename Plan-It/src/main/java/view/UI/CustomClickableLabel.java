package view.UI;

import core.GlobalResources;

import javax.swing.*;
import java.awt.*;

public class CustomClickableLabel extends JLabel {
    public CustomClickableLabel(String text) {
        super(text);
        setForeground(GlobalResources.COLOR_GREEN_1);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}
