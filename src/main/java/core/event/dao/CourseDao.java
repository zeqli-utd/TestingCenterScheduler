package core.event.dao;

import core.event.Course;

import java.util.List;

public interface CourseDao {
    List getAllCourse();

    Course findByCourseId(String courseId);

    boolean addCourse(Course course);

    boolean updateCourse(Course course, String id);

    boolean deleteCourse(String courseId);

    boolean deleteCoursesByTerm(int termId);
}
