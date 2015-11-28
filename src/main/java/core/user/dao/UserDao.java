package core.user.dao;

import core.user.Authorization;
import core.user.User;

import java.util.List;


public interface UserDao {
    List<User> getAllUserAccounts();
    List<User> getUserAccountsByName(String firstName, String lastName);
    User getUserAccountById(int Id);
    boolean updateInfo(User userAcco);
    boolean deleteUserAccount(String netid);
    boolean addUserAccount(User user);
}
