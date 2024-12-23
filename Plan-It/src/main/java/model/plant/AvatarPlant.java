package model.plant;

import core.SqLiteConnection;
import model.dao.avatarPlant.AvatarPlantDAOImpl;
import model.dao.avatarPlant.AvatarPlantDB;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class AvatarPlant {
    private static AvatarPlant instance; // Singleton instance

    private int id;
    private String name;
    private int hp;
    private int owner;

    private State happyState;
    private State sadState;
    private State normalState;
    private State currentState;

    // Private constructor to prevent instantiation
    private AvatarPlant() {
        happyState = new HappyState();
        sadState = new SadState();
        normalState = new NormalState();

        currentState = normalState; // Default state

        System.out.println("Plant created");
    }

    // Static method to get the singleton instance
    public static AvatarPlant getInstance() {
        if (instance == null) {
            synchronized (AvatarPlant.class) {
                if (instance == null) {
                    instance = new AvatarPlant();
                }
            }
        }
        return instance;
    }

    // Getters and setters
    public void setState(State state) {
        this.currentState = state;
    }

    public State getState() {
        return currentState;
    }

    public String getPathGifImage() {
        return currentState.getPathGifImage();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = clampHP(hp);
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public State getSadState() {
        return sadState;
    }

    public State getHappyState() {
        return happyState;
    }

    public State getNormalState() {
        return normalState;
    }

    public void loadPlant(int id_owner) {
        ArrayList<AvatarPlantDB> avatarPlants;
        try (Connection connection = SqLiteConnection.getInstance().getConnection()){
            AvatarPlantDAOImpl avatarPlantDAO = new AvatarPlantDAOImpl(connection);
            avatarPlants=avatarPlantDAO.getPlantsByOwnerId(id_owner);
        } catch (SQLException e) {throw new RuntimeException(e);}

        this.id = avatarPlants.get(0).getIdPlant();
        this.name = avatarPlants.get(0).getName();
        this.hp = avatarPlants.get(0).getHp();
        this.owner = avatarPlants.get(0).getOwner();

        System.out.println("Plant loaded: " + this.id + " " + this.name);
    }

    public void addHP(int value) {
        int newHP = clampHP(this.hp + value);
        updateHPInDatabase(newHP - this.hp);
        this.hp = newHP;
        System.out.println("HP added. Current HP: " + this.hp);
        updateState();
    }

    public void subtractHP(int value) {
        int newHP = clampHP(this.hp - value);
        updateHPInDatabase(newHP - this.hp);
        this.hp = newHP;
        System.out.println("HP subtracted. Current HP: " + this.hp);
        updateState();
    }

    private int clampHP(int value) {
        return Math.max(0, Math.min(value, 100));
    }

    private void updateHPInDatabase(int delta) {
        try (Connection connection = SqLiteConnection.getInstance().getConnection()){
            AvatarPlantDAOImpl avatarPlantDAO = new AvatarPlantDAOImpl(connection);
            AvatarPlantDB updatedPlant=new AvatarPlantDB(id,name,hp+delta,owner);
            avatarPlantDAO.updatePlant(updatedPlant);
        } catch (SQLException e) {throw new RuntimeException(e);}
    }

    public void updateState() {
        if (this.hp > 80) {
            setState(happyState);
        } else if (this.hp > 30) {
            setState(normalState);
        } else {
            setState(sadState);
        }

        System.out.println("Plant HP : " + this.hp);
    }
}
