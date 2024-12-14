package controller.commandPattern;

import core.ComponentManager;
import core.SqLiteConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GoBackCommand implements ActionCommand{
    private final String user, startFolder;
    public GoBackCommand(String user, String startFolder) {
        this.user = user;
        this.startFolder = startFolder;
    }
    @Override public void execute() {
        ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getDeskView(user,findParentFolder()));
    }

    public String findParentFolder() {
        String parentName = null;
        String query = """
            SELECT folder_name FROM Folder WHERE id=(
                SELECT parent
                FROM Folder
                WHERE folder_name=?
            );
        """;
        try (Connection connection = SqLiteConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {


                //per cercare sottocartelle di una cartella specifica
            statement.setString(1, startFolder); //folder name

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    parentName=(resultSet.getString("folder_name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching folders from database", e);
        }
        return parentName;
    }
}
