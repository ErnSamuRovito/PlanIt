package controller.controllers;

import model.dao.user.UserDB;
import model.services.UserService;

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public UserDB getUserByUsername(){
        return userService.getUserByUsername();
    }
}
