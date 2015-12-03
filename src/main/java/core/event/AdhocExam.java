package core.event;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public AdhocExam(String examId,
                     String examName,
                     int capacity,
                     int term,
                     LocalDateTime startDateTime,
                     LocalDateTime endDateTime,
                     String instructorId,
                     String courseId,
                     int duration) {
        super(examId, examName, capacity, term, startDateTime, endDateTime, instructorId, courseId, duration);
        this.examType = ExamType.AD_HOC;
        this.statusType = ExamStatusType.PENDING;
    }

    public List<StudentEntry> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<StudentEntry> studentList) {
        this.studentList = studentList;
    }
}
