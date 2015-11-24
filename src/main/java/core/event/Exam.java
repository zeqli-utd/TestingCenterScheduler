package core.event;

import core.event.dao.CourseDao;
import core.user.Instructor;
import core.user.dao.InstructorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
@Entity
@Table(name = "Exam")
public class Exam {
    @Id
    @Column(name = "examId")
    @Basic(optional = false)
    private String examId;

    @Column(name = "exam_name" )
    private String examName;

    @Basic(optional = false)
    @Column(name = "type" )
    private String type;

    @Basic(optional = false)
    @Column(name = "num_student_need_to_take_exam" )
    private int numStudentNeed;

    @Basic(optional = false)
    @Column(name = "num_student_existing_appointment_to_take_exam" )
    private int numStudentAppointment;

    @Column(name = "num_student_show_up" )
    private int numStudentShow;//

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="start_date_time")// start time of an exam
    @Basic(optional = false)
    private Date startDateTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="end_date_time")// end time of an exam
    @Basic(optional = false)
    private Date endDateTime;

    @Column(name="attendace")
    private int attendance;//TODO: should be double type

    @Column(name="instructor_id")
    private String instructorId;

    @Column(name = "course_id")
    private String courseId;

    private double duration;

    @Transient
    @Autowired
    private CourseDao courseDao;

    @Transient
    @Autowired
    private InstructorDao instructorDao;

    @Transient
    private String instructorName;

    @Transient
    private String courseName;

    public Exam(){

    }

    /**
     *
     * @param Id ExamID
     * @param type Type
     * @param start Start Time
     * @param end End Time
     * @param duration Duration
     * @param numApp Num of Application
     * @param numNeed Num of Needed
     */
    public Exam(String Id,
                String type,
                LocalDateTime start,
                LocalDateTime end,
                double duration,
                int numApp,
                int numNeed){
        examId = Id;
        this.type = type;

        Instant instant1 =
                start.atZone(ZoneId.systemDefault()).toInstant();
        startDateTime = Date.from(instant1);

        Instant instant2 =
                end.atZone(ZoneId.systemDefault()).toInstant();
        endDateTime = Date.from(instant2);

        this.duration = duration;

        numStudentAppointment = numApp;
        numStudentNeed = numNeed;
    }

    /**
     * Use this one in case of error
     * @param Id ExamID
     * @param type Type
     * @param start Start Time
     * @param end End Time
     * @param duration Duration
     * @param numApp Num of Application
     * @param numNeed Num of Needed
     * @param instructorId Instructor Id
     */
    public Exam(String Id,
                String type,
                LocalDateTime start,
                LocalDateTime end,
                double duration,
                int numApp,
                int numNeed,
                String instructorId) {
        this(Id, type, start, end, duration, numApp, numNeed);
        this.instructorId = instructorId;
    }

    /**
     * to unburden the front end, this method returns the name of the course
     * courseId is pointing to
     * @return subject
     */
    public String getCourseName () {
        return courseDao
                .findByCourseId(this.courseId)
                    .getSubject();
    }

    public String getInstructorName () {
        Instructor instructor =
                instructorDao.findByNetID(this.instructorId);

        return instructor.getLastName() + ", " + instructor.getFirstName();
    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public String getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public int getNumStudentShow() {
        return numStudentShow;
    }

    public void setNumStudentShow(int numStudentShow) {
        this.numStudentShow = numStudentShow;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public int getNumStudentAppointment() {
        return numStudentAppointment;
    }

    public void setNumStudentAppointment(int numStudentAppointment) {
        this.numStudentAppointment = numStudentAppointment;
    }

    public int getNumStudentNeed() {
        return numStudentNeed;
    }

    public void setNumStudentNeed(int numStudentNeed) {
        this.numStudentNeed= numStudentNeed;
    }

    public LocalDateTime getStartDateTime() {// date to
        Instant instant = Instant.ofEpochMilli(startDateTime.getTime());
        LocalDateTime res =
                LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        return res;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        Instant instant =
                startDateTime.atZone(ZoneId.systemDefault()).toInstant();
        this.startDateTime = Date.from(instant);
    }

    public LocalDateTime getEndDateTime() {
        Instant instant = Instant.ofEpochMilli(endDateTime.getTime());
        LocalDateTime res =
                LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        return res;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        Instant instant =
                endDateTime.atZone(ZoneId.systemDefault()).toInstant();
        this.endDateTime = Date.from(instant);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getDuration()
    {
        return (double)ChronoUnit.MINUTES.between(getStartDateTime(), getEndDateTime())/60;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
