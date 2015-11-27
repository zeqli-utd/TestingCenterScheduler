package core.event;

import core.user.Student;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * This is a class serves as a join table to handle mapping between Student and Exam.
 */

@Embeddable
public class StudentExamPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Exam exam;

    public void setExam(Exam exam){
        this.exam = exam;
    }

    public Exam getExam(){
        return exam;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}

