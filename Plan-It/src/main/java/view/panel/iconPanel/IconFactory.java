package view.panel.iconPanel;

import core.GlobalResources;

public class IconFactory {
    public static IconPanel createIconPanel(String type, String title) {
        switch (type) {
            case "task":
                return new IconPanel(title, GlobalResources.taskImage, null);
            case "folder":
                return new IconPanel(title, GlobalResources.folderImage, null);
            case "add":
                return new IconPanel(title, GlobalResources.addImage, null);
            default:
                throw new IllegalArgumentException("Unknown type: " + type);
        }
    }
}

