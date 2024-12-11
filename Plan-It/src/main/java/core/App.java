package core;

import model.Folder;
import model.Plant.AvatarPlant;
import model.Task;
import view.ApplicationWindow;

public class App {
    public static void main(String[] args) {
        // File singoli
        Task task1 = new Task("portare fuori il cane");
        Task task2 = new Task("chiamare zia");
        Task task3 = new Task("annaffiare basilico");

        // Cartelle
        Folder folder1 = new Folder("Social life");
        Folder folder2 = new Folder("Giardinaggio");
        Folder rootFolder = new Folder("Root");

        // Struttura della gerarchia
        folder1.add(task1);
        folder1.add(task2);
        folder1.add(folder2);

        folder2.add(task3);

        rootFolder.add(folder1);
        rootFolder.add(folder2);

        // Visualizzazione della struttura
        rootFolder.display();

        AvatarPlant.getInstance().setState(AvatarPlant.getInstance().getHappyState());
        ApplicationWindow window = ApplicationWindow.getInstance();
    }
}
