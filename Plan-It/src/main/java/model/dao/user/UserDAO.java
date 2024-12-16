package model.dao.user;
import java.util.List;

public interface UserDAO {
    void addUser(UserDB user);
    UserDB getUserById(int id);
    UserDB getUserByUsername(String username);
    List<UserDB> getAllUsers();
    void updateUser(UserDB user);
    void deleteUser(int id);
}

