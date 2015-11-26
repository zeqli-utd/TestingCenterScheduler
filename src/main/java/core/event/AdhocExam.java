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

    @ElementCollection
            @CollectionTable(name = "student_list",
                    joinColumns = {@JoinColumn(name = "net_id"), @JoinColumn(name = "first_name"),
                    @JoinColumn(name = "last_name")})
    Set<ArrayList> studentList;


    public AdhocExam(){
        studentList = new HashSet<>();
    }

    public Set<ArrayList> getStudentList() {
        return studentList;
    }

    public void setStudentList(Set<ArrayList> studentList) {
        this.studentList = studentList;
    }
}
