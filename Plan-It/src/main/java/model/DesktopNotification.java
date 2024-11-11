package model;

import java.io.IOException;

public class DesktopNotification {

    public static void sendNotification(String title, String message) {
        try {
            String[] command = { "notify-send", title, message };
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
