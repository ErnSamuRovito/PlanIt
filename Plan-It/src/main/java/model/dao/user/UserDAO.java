package model.dao.user;
import java.util.List;

public interface UserDAO {
    void addUser(User user);
    User getUserById(int id);
    User getUserByUsername(String username);
    List<User> getAllUsers();
    void updateUser(User user);
    void deleteUser(int id);
}

