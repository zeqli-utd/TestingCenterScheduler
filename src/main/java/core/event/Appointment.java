package core.event;

import core.service.SessionManager;
import core.user.Student;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @Column(name = "appointment_id")
    @GeneratedValue
    private int appointmentID;

    @Column(name = "exam_id")
    @Basic(optional = false)
    private String examId;

    @Column(name = "term")
    @Basic(optional = false)
    private int term;

    @Column(name = "made_by")
    @Basic(optional = false)
    private String madeBy;  // Student Id or AdminID

    @Column(name = "slot_id")
//    @Basic(optional = false)
    private String slotId;

    @Temporal(TemporalType.TIMESTAMP)
//    @Basic(optional = false)
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    private LocalDateTime startDateTime;

    @Temporal(TemporalType.TIMESTAMP)
//    @Basic(optional = false)
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    private LocalDateTime endDateTime;

    @Column(name="student_Id")
    @Basic(optional = false)
    private String studentId;

    //seat
    @Column(name="seat")
    private String seat;

    private boolean isSetAside;

    @Column(name="is_attend")
    @Basic(optional = false)
    private boolean isAttend;

    @Column(name="status")
    private String status;      // 's' marked superfluous || 'r' marked regular

    @Transient
    private String examName;

    public Appointment(){
        this.examId = "Default_Exam";
        this.term = 1158;
        this.madeBy = "Defaut_MadeBy";
        this.startDateTime = LocalDateTime.MIN;
        this.endDateTime = LocalDateTime.MIN;
        this.studentId = "Defaut_Student";
        this.seat = "Defaut_MadeBy";
        this.isAttend = false;
        this.status = "r";
        this.slotId = "Defaut_TimeSlot";
        isSetAside = false;
    }

    //LocalDateTime startDateTime, LocalDateTime endDateTime, String seat
    // are automatically assigned from Time Slots

    /**
     * Initialize an appointment
     * @param examId
     * @param madeBy
     * @param netId
     * @param begin
     * @param end
     */
    public Appointment(String examId,
                       String slotId,
                       String madeBy,
                       String netId,
                       LocalDateTime begin,
                       LocalDateTime end){
        this();
        this.examId = examId;
        this.slotId = slotId;
        this.madeBy = madeBy;
        this.studentId = netId;
        this.startDateTime = begin;
        this.endDateTime = end;
        this.seat = "";
        this.isAttend = false;
        this.status = "r";
        this.term = 1158;   // By Default
    }

    /**
     * Check if the attempted appointment is legal
     * @return
     */
    public boolean checkLegalAppointment(){
        String studentIdCheck = getStudentId();
        String examIdCheck = getExamId();
        int legalAppointment = 0;
        int size = -1;
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Student studentCheck = (Student)session.get(Student.class, studentIdCheck);
            Exam exam = (Exam)session.get(Exam.class, examIdCheck);
            //map to object
            Query query = session.createQuery("FROM Appointment a WHERE a.studentId = :stuId");
            query.setParameter("stuId", studentCheck.getNetId());
            List<Appointment> apptList = query.list();


            for (Appointment appt : apptList){
                //check b
                if(appt.getExamId().equals(examIdCheck)) {
                    //check c
                    if((getStartDateTime().isAfter(appt.getEndDateTime())) ||
                            (getEndDateTime().isBefore(appt.getStartDateTime()))) {
                        //check d
                        if ((getStartDateTime().isAfter(exam.getStartDateTime())) &&
                                (getEndDateTime().isBefore(exam.getEndDateTime()))) {
                            legalAppointment += 1;
                        }
                    }
                }
            }
        }catch (HibernateException he) {
            if(tx != null) {
                tx.rollback();
            }
            return false;
        } finally {
            session.close();
        }
        if(legalAppointment==size)
            return true;
        else
            return false;
    }

    /*-------------Getter and Setters-------------*/



    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public String getMadeBy() {
        return madeBy;
    }

    public void setMadeBy(String madeBy) {
        this.madeBy = madeBy;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public boolean isAttend() {
        return isAttend;
    }

    public void setIsAttend(boolean isAttend) {
        this.isAttend = isAttend;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public boolean isSetAside() {
        return isSetAside;
    }

    public void setIsSetAside(boolean isSetAside) {
        this.isSetAside = isSetAside;
    }

    // Legacy Code

//    public LocalDateTime getStartDateTime() {// date to
//        Instant instant = Instant.ofEpochMilli(startDateTime.getTime());
//        LocalDateTime res = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
//
//        return res;
//    }
//
//    public void setStartDateTime(LocalDateTime startDateTime) {
//        Instant instant = startDateTime.atZone(ZoneId.systemDefault()).toInstant();
//        this.startDateTime = Date.from(instant);
//    }
//
//    public LocalDateTime getEndDateTime() {
//        Instant instant = Instant.ofEpochMilli(endDateTime.getTime());
//        LocalDateTime res = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
//
//        return res;
//    }
//
//    public void setEndDateTime(LocalDateTime endDateTime) {
//        Instant instant = endDateTime.atZone(ZoneId.systemDefault()).toInstant();
//        this.endDateTime = Date.from(instant);
//    }
}
