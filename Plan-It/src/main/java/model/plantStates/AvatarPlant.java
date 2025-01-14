package model.plantStates;

import core.SqLiteConnection;
import model.utils.DateComparison;
import model.User;
import model.dao.avatarPlant.AvatarPlantDAOImpl;
import model.dao.avatarPlant.AvatarPlantDB;
import model.dao.task.TaskDAO;
import model.dao.task.TaskDAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class AvatarPlant {
    private static  AvatarPlant instance; // Thread-safe singleton

    private int id;
    private String name;
    private int hp;
    private int owner;


    private static final int PENANCE_LOW_TASK = 5;
    private static final int PENANCE_MEDIUM_TASK = 7;
    private static final int PENANCE_HIGH_TASK = 10;

    private final State happyState;
    private final State sadState;
    private final State normalState;
    private State currentState;

    private AvatarPlant() {
        this.happyState = new HappyState();
        this.sadState = new SadState();
        this.normalState = new NormalState();
        this.currentState = normalState;
    }

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


    public int getId() {
        return id;
    }

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
        this.hp = hp;
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

    public void loadPlant(int idOwner) {
        try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
            AvatarPlantDAOImpl avatarPlantDAO = new AvatarPlantDAOImpl(connection);
            List<AvatarPlantDB> avatarPlants = avatarPlantDAO.getPlantsByOwnerId(idOwner);

            if (!avatarPlants.isEmpty()) {
                AvatarPlantDB plantData = avatarPlants.get(0);
                this.id = plantData.getIdPlant();
                this.name = plantData.getName();
                this.hp = plantData.getHp();
                this.owner = plantData.getOwner();
            } else {
                throw new RuntimeException("No plants found for owner ID: " + idOwner);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error loading plant for owner ID: " + idOwner, e);
        }
    }

    public void addHP(int value) {
        int newHP = clampHP(this.hp + value);
        updateHPInDatabase(newHP);
        this.hp = newHP;
        updateState();
    }

    public void subtractHP(int value) {
        int newHP = clampHP(this.hp - value);
        updateHPInDatabase(newHP);
        this.hp = newHP;
        updateState();
    }

    private void updateHPInDatabase(int newHP) {
        try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
            AvatarPlantDAOImpl avatarPlantDAO = new AvatarPlantDAOImpl(connection);
            AvatarPlantDB updatedPlant = new AvatarPlantDB(id, name, newHP, owner);
            avatarPlantDAO.updatePlant(updatedPlant);
        } catch (SQLException e) {
            throw new RuntimeException("Error updating plant HP in database", e);
        }
    }

    private int clampHP(int value) {
        return Math.max(0, Math.min(100, value));
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

    public int getPenance() {
        int totalPenance = 0;
        DateComparison dateComparison = new DateComparison();

        HashMap<Integer, Integer> urgencyMap = new HashMap<>();
        urgencyMap.put(1, PENANCE_LOW_TASK);
        urgencyMap.put(2, PENANCE_MEDIUM_TASK);
        urgencyMap.put(3, PENANCE_HIGH_TASK);

        String query = "SELECT Task.id_task, Task.state, Task.due_date, Task.urgency FROM Task "
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

                    if (dateComparison.compareDate(dueDate) < 0 && resultSet.getInt("state") != -1) {
                        totalPenance += urgencyMap.getOrDefault(urgency, 0);
                        int taskId = resultSet.getInt("id_task");
                        TaskDAO taskDAO = new TaskDAOImpl(connection);
                        taskDAO.markTaskAsExpired(taskId);
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error calculating penance for user with owner ID: " + owner, e);
        }

        return totalPenance;
    }
}
