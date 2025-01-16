package model.persistance.dao.avatarPlant;

public class AvatarPlantDB {
    private int idPlant;
    private String name;
    private int hp;
    private int owner;

    public AvatarPlantDB(int idPlant, String name, int hp, int owner) {
        this.idPlant = idPlant;
        this.name = name;
        this.hp = hp;
        this.owner = owner;
    }

    public int getIdPlant() {return idPlant;}
    public void setIdPlant(int idPlant) {this.idPlant = idPlant;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public int getHp() {return hp;}
    public void setHp(int hp) {this.hp = hp;}
    public int getOwner() {return owner;}
    public void setOwner(int owner) {this.owner = owner;}
}
