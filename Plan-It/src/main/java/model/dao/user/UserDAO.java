package model.dao.user;
import java.util.List;

public interface UserDAO {
    UserDB getUserById(int id);
    UserDB getUserByUsername(String username);
    List<UserDB> getAllUsers();
    void updateUser(UserDB user);
    void setUsername(String username);
    boolean deleteUser(int id);
    String getHashedPassword(int id);
    void setPassword(int id, String password);

    int logUser(String user, String password);
    int registerUserAndGetId(String username, String email, String password);
}

