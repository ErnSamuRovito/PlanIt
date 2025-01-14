package model.dao.user;

import model.utils.PasswordUtils;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private final Connection connection;

    public UserDAOImpl(Connection connection) {
        this.connection = connection;
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
                return new UserDB(rs.getInt("id"), rs.getString("username"), rs.getString("email"), rs.getString("password"));
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

        // Poi cancelliamo la piantina associata all'utente
        String deleteAvatarPlantSql = "DELETE FROM AvatarPlant WHERE owner = ?";

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

            // Cancella la piantina associata all'utente
            try (PreparedStatement stmt = connection.prepareStatement(deleteAvatarPlantSql)) {
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
    public int logUser(String username, String plainPassword) {
        String query = "SELECT id, username, password FROM User WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String hashedPassword = resultSet.getString("password");

                    // Verifica la password usando il metodo verifyPassword
                    if (PasswordUtils.verifyPassword(plainPassword, hashedPassword)) {
                        return resultSet.getInt("id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Nessun utente trovato o password errata
    }

    public String getHashedPassword(int userId) {
        String sql = "SELECT password FROM User WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("password"); // Restituisce la password hashed
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Se non trovato, restituisce null
    }

    @Override
    public int registerUserAndGetId(String username, String email, String password) {
        String query = "INSERT INTO User (username, password, email) VALUES (?, ?, ?)";
        String hashedPassword = PasswordUtils.hashPassword(password);
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, hashedPassword);
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

    @Override
    public void setPassword(int id, String password) {
        String sql = "UPDATE User SET password = ? WHERE id = ?";
        String hashedPassword = PasswordUtils.hashPassword(password); // Hash della password
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, hashedPassword); // Usa la password hashata
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setEmail(String newEmail) {
        String sql = "UPDATE User SET email = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Imposta i parametri per il PreparedStatement
            preparedStatement.setString(1, newEmail); // nuova email
            preparedStatement.setInt(2, User.getInstance().getId()); // id dell'utente
            preparedStatement.executeUpdate(); // Esegui l'aggiornamento
        } catch (SQLException e) {
            System.err.println("Error updating email: " + e.getMessage());
            throw new RuntimeException("Error updating email", e); // Rilancia l'errore se qualcosa va storto
        }
    }

    @Override
    public boolean isUsernameTaken(String username) {
        String sql = "SELECT COUNT(*) FROM User WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Se il conteggio è maggiore di 0, l'username esiste già
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isEmailTaken(String email) {
        String sql = "SELECT COUNT(*) FROM User WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Se il conteggio è maggiore di 0, l'email esiste già
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
