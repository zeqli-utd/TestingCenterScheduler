package core.event;

import core.event.dao.CourseDao;
import core.user.dao.InstructorDao;
import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@Entity
@Table(name = "Exam")
public class Exam {
    @Id
    @Column (name = "exam_id")
    private String examId;      //

    @Basic(optional = false)
    @Column(name = "exam_name" )
    private String examName;    // CSE308-01_1158_ex2 or "Math Placement"

    @Basic(optional = false)
    @Column(name = "type" )
    private String type;        // regular or ad hoc

    @Basic(optional = false)
    @Column(name = "capacity")
    private int capacity;

    @Basic(optional = false)
    @Column(name = "term")
    private int term;           // 1158 for Fall 2015 or 0 for ad hoc exam

    @Temporal(TemporalType.TIMESTAMP)
    @Basic(optional = false)
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    private LocalDateTime startDateTime; // start time of an exam

    @Temporal(TemporalType.TIMESTAMP)
    @Basic(optional = false)
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    private LocalDateTime endDateTime;   // end time of an exam

    @Basic(optional = false)
    @Column(name = "instructor_id" )
    private String instructorId;    // NetId of the person who made this appointment

    @Basic(optional = false)
    @Column(name = "course_id" )
    private String courseId;    // CSE308-01_1158 or "adhoc" for ad hoc exam

    @Basic(optional = false)
    @Column(name = "duration")
    private int duration;   // Duration in minute

    @OneToMany (fetch = FetchType.LAZY, mappedBy = "id.exam")
    private List<Appointment> appointments;

    @Basic(optional = false)
    @Column(name = "num_appointments")
    private int numAppointments;

    @Transient
    @Autowired
    private CourseDao courseDao;

    @Transient
    @Autowired
    private InstructorDao instructorDao;

    @Transient
    private int numAttended;

    @Transient
    private double attendance;  // Calculate on the fly.

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
                int duration,
                int numApp,
                int numNeed){
        examId = Id;
        this.type = type;
        this.startDateTime = start;
        this.endDateTime = end;
        this.duration = duration;

        numAppointments = numApp;
        capacity = numNeed;
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
                int duration,
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

//    public String getMadeBy() {
//        Instructor instructor =
//                instructorDao.findByNetID(this.instructorId);
//
//        return instructor.getLastName() + ", " + instructor.getFirstName();
//    }





    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public double getAttendance() {
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

    public LocalDateTime getStartDateTime(){
        return startDateTime;
    }

    public LocalDateTime getEndDateTime(){
        return endDateTime;
    }
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public void setDuration(int duration) {
        this.duration = duration;
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

    public int getNumAppointments() {
        return numAppointments;
    }

    public void setNumAppointments(int numAppointments) {
        this.numAppointments = numAppointments;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int numStudentNeed) {
        this.capacity = numStudentNeed;
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

//    public double getDuration()
//    {
//        return (double)ChronoUnit.MINUTES.between(getStartDateTime(), getEndDateTime())/60;
//    }

    public int getDuration() { return duration;    }

    public int getDayDuration() {
        return (int)ChronoUnit.DAYS.between(startDateTime.toLocalDate(), endDateTime.toLocalDate());
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }



    // Legacy Code

//    public LocalDateTime getStartDateTime() {// date to
//        Instant instant = Instant.ofEpochMilli(startDateTime.getTime());
//        LocalDateTime res =
//                LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
//
//        return res;
//    }
//
//    public void setStartDateTime(LocalDateTime startDateTime) {
//        Instant instant =
//                startDateTime.atZone(ZoneId.systemDefault()).toInstant();
//        this.startDateTime = Date.from(instant);
//    }
//
//    public LocalDateTime getEndDateTime() {
//        Instant instant = Instant.ofEpochMilli(endDateTime.getTime());
//        LocalDateTime res =
//                LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
//
//        return res;
//    }
//
//    public void setEndDateTime(LocalDateTime endDateTime) {
//        Instant instant =
//                endDateTime.atZone(ZoneId.systemDefault()).toInstant();
//        this.endDateTime = Date.from(instant);
//    }
}
