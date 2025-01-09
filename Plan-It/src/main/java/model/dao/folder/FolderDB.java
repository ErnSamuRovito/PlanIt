package model.dao.folder;

public class FolderDB {
    private int id;
    private String folderName;
    private int owner;
    private int parent;

    public FolderDB(String folderName, int owner, int parent) {
        //this.id = id;
        this.folderName = folderName;
        this.owner = owner;
        this.parent = parent;
    }

    public int getId() {return id;}
    public String getFolderName() {return folderName;}
    public int getOwner() {return owner;}
    public int getParent() {return parent;}

    public void setId(int id) {this.id = id;}
    public void setFolderName(String folderName) {this.folderName = folderName;}
    public void setOwner(int owner) {this.owner = owner;}
    public void setParent(int parent) {this.parent = parent;}
}
