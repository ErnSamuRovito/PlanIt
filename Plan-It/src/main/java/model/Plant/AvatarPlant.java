package model.Plant;

import core.SqLiteConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        String query = "SELECT * FROM AvatarPlant WHERE owner = ?";
        try (Connection connection = SqLiteConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id_owner);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    this.id = resultSet.getInt("id_plant");
                    this.name = resultSet.getString("name");
                    this.hp = resultSet.getInt("hp");
                    this.owner = resultSet.getInt("owner");
                }
            }
            System.out.println("Plant loaded: " + this.id + " " + this.name);

        } catch (SQLException e) {
            System.err.println("Error loading plant: " + e.getMessage());
        }
    }

    public void addHP(int value) {
        int newHP = clampHP(this.hp + value);
        updateHPInDatabase(newHP - this.hp); // Update only the difference
        this.hp = newHP;
        System.out.println("HP added. Current HP: " + this.hp);
        updateState();
    }

    public void subtractHP(int value) {
        int newHP = clampHP(this.hp - value);
        updateHPInDatabase(newHP - this.hp); // Update only the difference
        this.hp = newHP;
        System.out.println("HP subtracted. Current HP: " + this.hp);
        updateState();
    }

    // Helper method to ensure HP is within bounds
    private int clampHP(int value) {
        return Math.max(0, Math.min(value, 100));
    }

    // Updates the HP in the database
    private void updateHPInDatabase(int delta) {
        String query = "UPDATE AvatarPlant SET hp = hp + ? WHERE owner = ?";
        try (Connection connection = SqLiteConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, delta);
            preparedStatement.setInt(2, owner);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error updating HP in database: " + e.getMessage());
        }
    }

    // Updates the current state based on HP
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
