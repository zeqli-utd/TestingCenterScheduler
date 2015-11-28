package core.user.dao;

import core.user.Authorization;
import core.user.Student;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentDao {
    List<Student> getAllStudents();
    List<Student> getStudentsByName(String firstName, String lastName);
    Student getStudentById(int Id);

    boolean updateInfo(Student student);
    boolean deleteStudent(Student student);
    boolean addStudent(Student student);
}
