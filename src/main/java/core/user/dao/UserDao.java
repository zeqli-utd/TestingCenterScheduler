package core.user.dao;

import core.user.Authorization;
import core.user.User;

import java.util.List;


public interface UserDao {
    List<User> getAllUsers();
    List<User> getUserByName(String firstName, String lastName);
    User getUserById(int netid);
    boolean updateInfo(User user);
    boolean deleteUser(String netid);
    boolean addUser(User user);
}
