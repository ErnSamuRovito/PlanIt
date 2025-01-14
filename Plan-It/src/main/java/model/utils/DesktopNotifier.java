package model.utils;

import java.awt.*;
import java.io.*;

public class DesktopNotifier {
    public void sendNotification(String title, String message) {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            sendWindowsNotification(title, message);
        } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
            sendLinuxMacNotification(title, message, os);
        } else {
            System.out.println("Sistema operativo non supportato");
        }
    }

    private void sendWindowsNotification(String title, String message) {
        try {
            SystemTray systemTray = SystemTray.getSystemTray();
            TrayIcon trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().createImage("icon.png"));
            systemTray.add(trayIcon);
            trayIcon.displayMessage(title, message, TrayIcon.MessageType.INFO);
        } catch (AWTException e) {
            System.out.println("Errore nel creare la notifica su Windows: " + e.getMessage());
        }
    }

    private void sendLinuxMacNotification(String title, String message, String os) {
        try {
            String command = "";
            if (os.contains("nix") || os.contains("nux")) {
                // Linux
                command = "notify-send \"" + title + "\" \"" + message + "\"";
            } else if (os.contains("mac")) {
                // macOS
                command = "osascript -e 'display notification \"" + message + "\" with title \"" + title + "\"'";
            }

            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println("Errore nel creare la notifica su Linux/macOS: " + e.getMessage());
        }
    }
}
