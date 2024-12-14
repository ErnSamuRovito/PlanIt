package view.panel.iconPanel;

import controller.commandPattern.ActionCommand;
import core.GlobalResources;

public class IconFactory {
    public static IconPanel createIconPanel(String type, String title, ActionCommand command) {
        switch (type) {
            case "task":
                return new IconPanel(title, GlobalResources.taskImage, command);
            case "folder":
                return new IconPanel(title, GlobalResources.folderImage, command);
            case "add":
                return new IconPanel(title, GlobalResources.addImage, command);
            case "back":
                return new IconPanel(title, GlobalResources.backImage, command);
            default:
                throw new IllegalArgumentException("Unknown type: " + type);
        }
    }
}

