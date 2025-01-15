package model.dao.folder;

import model.composite.Folder;

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
    public boolean addFolder(Folder folder) {
        // Verifica se la cartella con lo stesso nome, owner e parent esiste già
        String checkSql = "SELECT COUNT(*) FROM Folder WHERE folder_name = ? AND owner = ? AND parent = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
            checkStmt.setString(1, folder.getFolderName());
            checkStmt.setInt(2, folder.getOwner());
            checkStmt.setInt(3, folder.getParent());

            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return false;
                }
            }

            // Inserimento della nuova cartella
            String sql = "INSERT INTO Folder (folder_name, owner, parent) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, folder.getFolderName());
                stmt.setInt(2, folder.getOwner());
                stmt.setInt(3, folder.getParent());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Boolean addRootFolder(int owner){
        String sql = "INSERT INTO Folder (folder_name,owner,parent) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "/root");
            stmt.setInt(2, owner);
            stmt.setNull(3, java.sql.Types.INTEGER); //parent = null
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Folder getFolderById(int id) {
        String sql = "SELECT * FROM Folder WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Folder(rs.getString("folder_name"), rs.getInt("owner"), rs.getInt("parent"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getFolderIdByNameAndOwner(String name,String owner){
        String sql = "SELECT id FROM Folder WHERE folder_name = ? and owner in (SELECT id FROM User WHERE username=?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, owner);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public List<Folder> getFoldersByOwner(int ownerId) {
        List<Folder> folders = new ArrayList<>();
        String sql = "SELECT * FROM Folder WHERE owner = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, ownerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                folders.add(new Folder(rs.getString("folder_name"), rs.getInt("owner"), rs.getInt("parent")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return folders;
    }

    @Override
    public List<Folder> getSubfolders(int parentId) {
        List<Folder> folders = new ArrayList<>();
        String sql = "SELECT * FROM Folder WHERE parent = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, parentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                folders.add(new Folder(rs.getString("folder_name"), rs.getInt("owner"), rs.getInt("parent")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return folders;
    }

    @Override
    public boolean updateFolder(Folder folder, int id) {
        // Verifica se esiste già una cartella con lo stesso nome, owner e parent, ma con ID diverso
        String checkSql = "SELECT COUNT(*) FROM Folder WHERE folder_name = ? AND owner = ? AND parent = ? AND id != ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
            checkStmt.setString(1, folder.getFolderName());
            checkStmt.setInt(2, folder.getOwner());
            checkStmt.setInt(3, folder.getParent());
            checkStmt.setInt(4, id);

            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    // Se esiste già una cartella con lo stesso nome, owner e parent (ma con ID diverso), restituisce falso.
                    return false;
                }
            }

            // Se non esiste, esegui l'aggiornamento
            String sql = "UPDATE Folder SET folder_name = ?, owner = ?, parent = ? WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, folder.getFolderName());
                stmt.setInt(2, folder.getOwner());
                stmt.setInt(3, folder.getParent());
                stmt.setInt(4, id);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void deleteFolder(int id) {
        String deleteTasksSql = """
    DELETE FROM Task 
    WHERE folder IN (
        WITH RECURSIVE subfolders AS (
            SELECT id FROM Folder WHERE id = ?
            UNION ALL
            SELECT f.id FROM Folder f
            INNER JOIN subfolders sf ON f.parent = sf.id
        )
        SELECT id FROM subfolders
    )
    """;

        String deleteFoldersSql = """
    DELETE FROM Folder 
    WHERE id IN (
        WITH RECURSIVE subfolders AS (
            SELECT id FROM Folder WHERE id = ?
            UNION ALL
            SELECT f.id FROM Folder f
            INNER JOIN subfolders sf ON f.parent = sf.id
        )
        SELECT id FROM subfolders
    )
    """;

        try {
            connection.setAutoCommit(false); // Inizia una transazione

            try (PreparedStatement deleteTasksStmt = connection.prepareStatement(deleteTasksSql);
                 PreparedStatement deleteFoldersStmt = connection.prepareStatement(deleteFoldersSql)) {

                // Elimina i task associati
                deleteTasksStmt.setInt(1, id);
                deleteTasksStmt.executeUpdate();

                // Elimina le cartelle
                deleteFoldersStmt.setInt(1, id);
                deleteFoldersStmt.executeUpdate();

                connection.commit(); // Conferma la transazione
            } catch (SQLException e) {
                connection.rollback(); // Rollback in caso di errore
                throw new RuntimeException("Errore durante l'eliminazione della folder e dei relativi task", e);
            } finally {
                connection.setAutoCommit(true); // Ripristina il comportamento di default
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore nella gestione della transazione", e);
        }
    }

    @Override
    public int findParentFolder(int folderId) {
        int parentId = -1;
        String query = """
        SELECT parent FROM Folder WHERE id = ?;
        """;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, folderId); // Imposta il nome della cartella di cui cercare il parent

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) { // Se esiste un risultato
                    parentId = resultSet.getInt("parent");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching parent folder from database", e);
        }
        return parentId; // Ritorna il nome della cartella genitore o null se non trovato
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

    @Override
    public boolean updateFolderParent(int folderId, int newParentId) {
        String sql = "UPDATE Folder SET parent = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, newParentId);
            stmt.setInt(2, folderId);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean checkFolderExistsInParent(int parentId, Integer folderId) {
        String sql = """
            SELECT COUNT(*)
            FROM Folder
            WHERE parent = ? AND folder_name = (
                SELECT folder_name FROM Folder WHERE id = ?
            );
        """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, parentId);
            stmt.setInt(2, folderId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.getInt(1) > 0; // Restituisce true se esiste una cartella con lo stesso nome
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Se si verifica un errore, restituisce false
        }
    }
}
