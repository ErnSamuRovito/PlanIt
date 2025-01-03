package model.dao.user;
import java.util.List;

public interface UserDAO {
    void addUser(UserDB user);
    UserDB getUserById(int id);
    UserDB getUserByUsername(String username);
    List<UserDB> getAllUsers();
    void updateUser(UserDB user);
    void setUsername(String username);
    void deleteUser(int id);

    int logUser(String user, String password);
    int registerUserAndGetId(String username, String email, String password);
}

