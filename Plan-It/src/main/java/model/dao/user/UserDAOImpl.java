package model.dao.user;

import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private Connection connection;

    public UserDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addUser(UserDB user) {
        String sql = "INSERT INTO User (password, email, username) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getPassword());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getUsername());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UserDB getUserById(int id) {
        String sql = "SELECT * FROM User WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new UserDB(rs.getInt("id"), rs.getString("username"), rs.getString("email"), rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public UserDB getUserByUsername(String username) {
        String sql = "SELECT * FROM User WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new UserDB(rs.getInt("id"), rs.getString("password"), rs.getString("email"), rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<UserDB> getAllUsers() {
        List<UserDB> users = new ArrayList<>();
        String sql = "SELECT * FROM User";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(new UserDB(rs.getInt("id"), rs.getString("password"), rs.getString("email"), rs.getString("username")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void updateUser(UserDB user) {
        String sql = "UPDATE User SET password = ?, email = ?, username = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getPassword());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getUsername());
            stmt.setInt(4, user.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteUser(int id) {
        // Prima cancelliamo tutti i task associati all'utente
        String deleteTasksSql = "DELETE FROM Task WHERE folder IN (SELECT id FROM Folder WHERE owner = ?)";

        // Poi cancelliamo tutte le cartelle associate all'utente
        String deleteFoldersSql = "DELETE FROM Folder WHERE owner = ?";

        // Infine, cancelliamo l'utente
        String deleteUserSql = "DELETE FROM User WHERE id = ?";

        try {
            // Disabilita il commit automatico per migliorare le performance
            connection.setAutoCommit(false);

            // Cancella i task associati all'utente
            try (PreparedStatement stmt = connection.prepareStatement(deleteTasksSql)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }

            // Cancella le cartelle dell'utente
            try (PreparedStatement stmt = connection.prepareStatement(deleteFoldersSql)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }

            // Cancella l'utente
            try (PreparedStatement stmt = connection.prepareStatement(deleteUserSql)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }

            // Esegui il commit delle modifiche
            connection.commit();

            return true; // Tutto è andato a buon fine

        } catch (SQLException e) {
            // In caso di errore, annulla tutte le modifiche
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false; // C'è stato un errore, ritorniamo false
        } finally {
            // Ripristina il comportamento predefinito del commit
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int logUser(String user, String password) {
        String query = "SELECT id FROM User WHERE (username = ? OR email = ?) AND password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Imposta i parametri per il PreparedStatement
            preparedStatement.setString(1, user); // username
            preparedStatement.setString(2, user); // email
            preparedStatement.setString(3, password); // password

            // Esegui la query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                } else {
                    return -1; // Nessun utente trovato
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching for user: " + e.getMessage());
            throw new RuntimeException("Error searching for user", e);
        }
    }

    @Override
    public int registerUserAndGetId(String username, String email, String password) {
        String query = "INSERT INTO User (username, password, email) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); // Restituisci l'ID generato
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error inserting user: " + e.getMessage());
        }
        return -1;
    }

    public void setUsername(String newUsername) {
       String query = "UPDATE User SET username = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Imposta i parametri per il PreparedStatement
            preparedStatement.setString(1, newUsername); // username
            preparedStatement.setInt(2, User.getInstance().getId()); // id
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error searching for user: " + e.getMessage());
            throw new RuntimeException("Error searching for user", e);
        }
    }
}
