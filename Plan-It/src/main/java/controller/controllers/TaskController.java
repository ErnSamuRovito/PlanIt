package controller.controllers;

import model.services.TaskService;

import java.util.ArrayList;

public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    public int getTaskState(String startFolder, String user, String title) {
        return taskService.getTaskState(startFolder, user, title);
    }

    public ArrayList<String> getTaskData(String title, String user, String startFolder) {
        return taskService.getTaskData(title, startFolder, user);
    }
}

