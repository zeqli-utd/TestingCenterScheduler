package core.user.dao;

import core.user.Administrator;
import core.user.Instructor;
import core.user.Student;
import core.user.UserType;

import java.util.List;

public interface UserDao {
    UserType getUserById(String Id);

    List<UserType> getUserByName(String lastName, String fistName);

    List<UserType> getAllUser();

    List<Administrator> getAllAdministrators();

    List<Instructor> getAllInstructors();

    List<Student> getAllStudents();

    boolean addUser(UserType user);

    boolean deleteUser(UserType user);

    boolean updateUser(UserType user);
}
