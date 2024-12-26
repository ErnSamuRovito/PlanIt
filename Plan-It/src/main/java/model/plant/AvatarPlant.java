package model.plant;

import controller.commandPattern.GoToTaskViewCommand;
import core.SqLiteConnection;
import model.DateComparison;
import model.User;
import model.dao.avatarPlant.AvatarPlantDAOImpl;
import model.dao.avatarPlant.AvatarPlantDB;
import model.dao.task.TaskDAOImpl;
import org.sqlite.SQLiteConnection;
import view.panel.iconPanel.IconFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AvatarPlant {
    private static AvatarPlant instance; // Singleton instance

    private int id;
    private String name;
    private int hp;
    private int owner;

    private final int PENANCE_LOW_TASK = 5;
    private final int PENANCE_MEDIUM_TASK = 7;
    private final int PENANCE_HIGH_TASK = 10;

    private State happyState;
    private State sadState;
    private State normalState;
    private State currentState;

    private AvatarPlant() {
        happyState = new HappyState();
        sadState = new SadState();
        normalState = new NormalState();

        currentState = normalState; // Default state

        calculatePenance();
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
        try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
            AvatarPlantDAOImpl avatarPlantDAO = new AvatarPlantDAOImpl(connection);
            avatarPlants = avatarPlantDAO.getPlantsByOwnerId(id_owner);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        this.id = avatarPlants.get(0).getIdPlant();
        this.name = avatarPlants.get(0).getName();
        this.hp = avatarPlants.get(0).getHp();
        this.owner = avatarPlants.get(0).getOwner();

        System.out.println("Plant loaded: " + this.id + " " + this.name);
    }

    public void addHP(int value) {
        int newHP = clampHP(this.hp + value);
        updateHPInDatabase(newHP);
        this.hp = newHP;
        System.out.println("HP added. Current HP: " + this.hp);
        updateState();
    }

    public void subtractHP(int value) {
        int newHP = clampHP(this.hp - value);
        updateHPInDatabase(newHP);
        this.hp = newHP;
        System.out.println("HP subtracted. Current HP: " + this.hp);
        updateState();
    }

    private void updateHPInDatabase(int newHP) {
        try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
            AvatarPlantDAOImpl avatarPlantDAO = new AvatarPlantDAOImpl(connection);
            AvatarPlantDB updatedPlant = new AvatarPlantDB(id, name, newHP, owner);
            avatarPlantDAO.updatePlant(updatedPlant);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int clampHP(int value) {
        return Math.max(0, Math.min(value, 100));
    }

    public void updateState() {
        if (this.hp > 80) {
            setState(happyState);
        } else if (this.hp > 30) {
            setState(normalState);
        } else {
            setState(sadState);
        }
    }

    public void calculatePenance() {
        int totalPenance = 0;
        DateComparison dateComparison = new DateComparison();

        HashMap<Integer, Integer> urgencyMap = new HashMap<>();
        urgencyMap.put(1, PENANCE_LOW_TASK);
        urgencyMap.put(2, PENANCE_MEDIUM_TASK);
        urgencyMap.put(3, PENANCE_HIGH_TASK);

        String query = "SELECT Task.due_date, Task.urgency FROM Task "
                + "JOIN Folder ON Task.folder = Folder.id "
                + "JOIN User ON Folder.owner = User.id "
                + "WHERE User.id = ?";

        try (Connection connection = SqLiteConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, User.getInstance().getId());

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String dueDate = resultSet.getString("due_date");
                    int urgency = resultSet.getInt("urgency");

                    if (dateComparison.compareDate(dueDate) < 0) {
                        totalPenance += urgencyMap.getOrDefault(urgency, 0);
                    }
                }
            }

            subtractHP(totalPenance);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error calculating penance for user with owner ID: " + owner, e);
        }

        System.out.println("Total Penance: " + totalPenance);
    }
}