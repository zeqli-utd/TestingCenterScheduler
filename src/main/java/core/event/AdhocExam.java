package core.event;

import core.event.dao.CourseDao;
import core.user.Student;
import core.user.dao.InstructorDao;
import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * This class stores ad hoc exam information about ad hoc exam.
 * Remarkably, it keeps a list of student netId, first name, and last name, of which instructor entered in the textfield
 */
@Entity
@Table(name = "adhoc_exam")
public class AdhocExam extends Exam{

    @ElementCollection(fetch = FetchType.EAGER)
    List<StudentEntry> studentList = new ArrayList<>();

    public AdhocExam() {
    }

    public List<StudentEntry> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<StudentEntry> studentList) {
        this.studentList = studentList;
    }
}
