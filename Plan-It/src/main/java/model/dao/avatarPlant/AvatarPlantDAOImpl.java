package model.dao.avatarPlant;

import model.dao.user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvatarPlantDAOImpl implements AvatarPlantDAO {
    private Connection connection;

    public AvatarPlantDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addPlant(AvatarPlant plant) {
        String sql = "INSERT INTO AvatarPlant (name,hp,owner) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, plant.getName());
            stmt.setInt(2, plant.getHp());
            stmt.setInt(3, plant.getOwner());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public AvatarPlant getPlantById(int id) {
        String sql = "SELECT * FROM AvatarPlant WHERE id_plant = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new AvatarPlant(rs.getInt("id_plant"), rs.getString("name"), rs.getInt("hp"), rs.getInt("owner"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<AvatarPlant> getPlantsByOwner(int ownerId) {
        List<AvatarPlant> plants = new ArrayList<>();
        String sql = "SELECT * FROM AvatarPlant WHERE owner = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, ownerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                plants.add(new AvatarPlant(rs.getInt("id_plant"), rs.getString("name"), rs.getInt("hp"), rs.getInt("owner")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plants;
    }

    @Override
    public List<AvatarPlant> getAllPlants() {
        List<AvatarPlant> plants = new ArrayList<>();
        String sql = "SELECT * FROM User";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                plants.add(new AvatarPlant(rs.getInt("id_plant"), rs.getString("name"), rs.getInt("hp"), rs.getInt("owner")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plants;
    }

    @Override
    public void updatePlant(AvatarPlant plant) {
        String sql = "UPDATE AvatarPlant SET name = ?, hp = ?, owner = ? WHERE id_plant = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, plant.getName());
            stmt.setInt(2, plant.getHp());
            stmt.setInt(3, plant.getOwner());
            stmt.setInt(4, plant.getIdPlant());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePlant(int id) {
        String sql = "DELETE FROM AvatarPlant WHERE id_plant = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
