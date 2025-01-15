package model.services;

import core.SqLiteConnection;
import model.composite.Task;
import model.dao.task.TaskDAOImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class TaskService {
    public int getTaskState(String startFolder, String user, String title) {
        try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
            TaskDAOImpl taskDAO = new TaskDAOImpl(connection);
            return taskDAO.checkTaskByFolderAndTitle(startFolder, user, title);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> getTaskData(String title, String startFolder, String user) {
        try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
            TaskDAOImpl taskDAO = new TaskDAOImpl(connection);
            return taskDAO.getTaskDataByTitleAndFolderAndUsername(title, startFolder, user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Task> getTasksDueToday(String username) {
        try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
            TaskDAOImpl taskDAO = new TaskDAOImpl(connection);
            return taskDAO.getTasksDueTodayByUser(username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getFolderNameByTaskTitle(String taskTitle) {
        try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
            TaskDAOImpl taskDAO = new TaskDAOImpl(connection);
            return taskDAO.getFolderNameByTaskTitle(taskTitle); // Richiama il DAO
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
