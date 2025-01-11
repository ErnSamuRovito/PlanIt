package model.composite;

import java.util.ArrayList;

public class Folder implements FileSystemComponent{
    private int id;
    private String folderName;
    private int owner;
    private int parent;
    private ArrayList<FileSystemComponent> children;

    public Folder(String folderName, int owner, int parent) {
        //this.id = id;
        this.folderName = folderName;
        this.owner = owner;
        this.parent = parent;
        children = new ArrayList<>();
    }

    public int getId() {return id;}
    public String getFolderName() {return folderName;}
    public int getOwner() {return owner;}
    public int getParent() {return parent;}

    public void setId(int id) {this.id = id;}
    public void setFolderName(String folderName) {this.folderName = folderName;}
    public void setOwner(int owner) {this.owner = owner;}
    public void setParent(int parent) {this.parent = parent;}

    @Override
    public void display() {
        System.out.println("Folder: " + folderName);
        for (FileSystemComponent child : children) {
            child.display();
        }
    }

    public void add(FileSystemComponent component) {
        children.add(component);
    }

    public void remove(FileSystemComponent component) {
        children.remove(component);
    }

    public ArrayList<FileSystemComponent> getChildren() {return children;}
}
