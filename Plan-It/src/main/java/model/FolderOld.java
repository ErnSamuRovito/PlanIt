package model;

import java.util.ArrayList;
import java.util.List;

public class FolderOld implements SystemComponent{
    private String name;
    private List<SystemComponent> components = new ArrayList<>();

    public FolderOld(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void display() {
        System.out.println("Folder : " + name);
        System.out.println("---------------");
        for (SystemComponent component : components) {
            component.display();
        }
        System.out.println("---------------");
    }

    public void add(SystemComponent component) {
        components.add(component);
    }

    public void remove(SystemComponent component) {
        components.remove(component);
    }
}
