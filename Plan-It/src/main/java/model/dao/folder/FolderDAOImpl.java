package model.dao.folder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FolderDAOImpl implements FolderDAO {
    private Connection connection;

    public FolderDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addFolder(FolderDB folder) {
        String sql = "INSERT INTO Folder (folder_name,owner,parent) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, folder.getFolderName());
            stmt.setInt(2, folder.getOwner());
            stmt.setInt(3, folder.getParent());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public FolderDB getFolderById(int id) {
        String sql = "SELECT * FROM Folder WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new FolderDB(rs.getInt("id"), rs.getString("folder_name"), rs.getInt("owner"), rs.getInt("parent"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<FolderDB> getFoldersByOwner(int ownerId) {
        List<FolderDB> folders = new ArrayList<>();
        String sql = "SELECT * FROM Folder WHERE owner = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, ownerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                folders.add(new FolderDB(rs.getInt("id"), rs.getString("folder_name"), rs.getInt("owner"), rs.getInt("parent")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return folders;
    }

    @Override
    public List<FolderDB> getSubfolders(int parentId) {
        List<FolderDB> folders = new ArrayList<>();
        String sql = "SELECT * FROM Folder WHERE parent = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, parentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                folders.add(new FolderDB(rs.getInt("id"), rs.getString("folder_name"), rs.getInt("owner"), rs.getInt("parent")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return folders;
    }

    @Override
    public void updateFolder(FolderDB folder) {
        String sql = "UPDATE Folder SET folder_name = ?, owner = ?, parent = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, folder.getFolderName());
            stmt.setInt(2, folder.getOwner());
            stmt.setInt(3, folder.getParent());
            stmt.setInt(4, folder.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteFolder(int id) {
        String sql = "DELETE FROM Folder WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getFoldersByFolderAndUser(String startFolder, String user) {
        List<String> folders = new ArrayList<>();
        String query = """
            SELECT folder_name
            FROM Folder
            WHERE (parent = (
                    SELECT id
                    FROM Folder
                    WHERE folder_name = ?
                    AND owner IN (
                        SELECT id
                        FROM User
                        WHERE username = ? OR email = ?
                    )
                ) OR (parent IS NULL AND ? IS NULL))
            AND owner IN (
                SELECT id
                FROM User
                WHERE username = ? OR email = ?
            );
        """;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            if (startFolder.equals("/")) {
                statement.setNull(1, java.sql.Types.VARCHAR); // folder name
                statement.setNull(4, java.sql.Types.VARCHAR); // folder name
            } else {
                statement.setString(1, startFolder); // folder name
                statement.setString(4, startFolder); // folder name
            }
            statement.setString(2, user); // username or email
            statement.setString(3, user); // username or email
            statement.setString(5, user); // username or email
            statement.setString(6, user); // username or email

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    folders.add(resultSet.getString("folder_name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching folders from database", e);
        }
        return folders;
    }
}
