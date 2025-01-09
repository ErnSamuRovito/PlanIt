package model.dao.avatarPlant;

import model.plant.AvatarPlant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvatarPlantDAOImpl implements AvatarPlantDAO {
    private Connection connection;

    public AvatarPlantDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Boolean addPlant(AvatarPlantDB plant) {
        String sql = "INSERT INTO AvatarPlant (name,hp,owner) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, plant.getName());
            stmt.setInt(2, plant.getHp());
            stmt.setInt(3, plant.getOwner());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public AvatarPlantDB getPlantById(int id) {
        String sql = "SELECT * FROM AvatarPlant WHERE id_plant = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new AvatarPlantDB(rs.getInt("id_plant"), rs.getString("name"), rs.getInt("hp"), rs.getInt("owner"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<AvatarPlantDB> getPlantsByOwnerId(int ownerId) {
        ArrayList<AvatarPlantDB> plants = new ArrayList<>();
        String sql = "SELECT * FROM AvatarPlant WHERE owner = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, ownerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                plants.add(new AvatarPlantDB(rs.getInt("id_plant"), rs.getString("name"), rs.getInt("hp"), rs.getInt("owner")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plants;
    }


    @Override
    public ArrayList<AvatarPlantDB> getPlantsByOwnerName(String ownerName) {
        ArrayList<AvatarPlantDB> plants = new ArrayList<>();
        String sql = "SELECT * FROM AvatarPlant WHERE owner IN (SELECT id FROM User WHERE username=?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, ownerName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                plants.add(new AvatarPlantDB(rs.getInt("id_plant"), rs.getString("name"), rs.getInt("hp"), rs.getInt("owner")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plants;
    }

    @Override
    public List<AvatarPlantDB> getAllPlants() {
        List<AvatarPlantDB> plants = new ArrayList<>();
        String sql = "SELECT * FROM User";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                plants.add(new AvatarPlantDB(rs.getInt("id_plant"), rs.getString("name"), rs.getInt("hp"), rs.getInt("owner")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plants;
    }

    @Override
    public void setName(String name) {
        String sql = "UPDATE AvatarPlant SET name = ? WHERE id_plant = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setInt(2, AvatarPlant.getInstance().getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePlant(AvatarPlantDB plant) {
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
