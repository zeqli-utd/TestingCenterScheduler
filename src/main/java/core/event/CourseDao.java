package core.event;

import java.util.List;

public interface CourseDao {
    List<Course> getAllCourse();

    Course findByCourseId(String courseId);

    boolean addCourse(Course course);

    boolean updateCourse(Course course, String id);

    boolean deleteCourse(Course course);

}
