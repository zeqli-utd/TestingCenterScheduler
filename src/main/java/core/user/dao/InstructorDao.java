package core.user.dao;

import core.user.Instructor;

import java.util.List;

public interface InstructorDao {
    List<Instructor> findAll();
    Instructor findByNetID(String id);

    boolean addInstructor(Instructor instructor);
    boolean updateInstructor(Instructor instructor, String id);
    boolean deleteInstructor(Instructor instructor);
}
