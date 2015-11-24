package core.event;

import core.service.SessionManager;
import core.user.Student;
import org.hibernate.*;
import org.hibernate.Query;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Entity
public class Appointment {
    @Id
    @Column(name = "appointment_id")
    private String appointmentID;

    @Column(name = "exam_id")
    @Basic(optional = false)
    private String examId;

    @Column(name = "made_by")
    @Basic(optional = false)
    private String madeBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="start_time", nullable = false)
    private Date startDateTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="end_time")
    @Basic(optional = false)
    private Date endDateTime;

    @Column(name="student_Id")
    @Basic(optional = false)
    private String studentId;

    @Column(name="seat")
    @Basic(optional = false)
    private String seat;

    @Column(name="is_attend")
    @Basic(optional = false)
    private boolean isAttend;

    @Column(name="status")
    private String status;

    public Appointment(){}

    public Appointment(String appointmentID, String examId, String madeBy, LocalDateTime startDateTime,
                       LocalDateTime endDateTime, String netId, String seat, boolean isAttend){
        this.appointmentID = appointmentID;
        this.examId = examId;
        this.madeBy = madeBy;
        Instant instant1 = startDateTime.atZone(ZoneId.systemDefault()).toInstant();
        this.startDateTime = Date.from(instant1);
        Instant instant2 = endDateTime.atZone(ZoneId.systemDefault()).toInstant();
        this.endDateTime = Date.from(instant2);
        this.studentId = netId;
        this.seat = seat;
        this.isAttend = isAttend;
    }

    public boolean checkLegalAppointment(){
        String studentIdCheck = getStudentId();
        String examIdCheck = getExamId();
        int legalAppointment = 0;
        int size = -1;
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Student studentCheck = (Student)session.get(Student.class, studentIdCheck);
            Exam exam = (Exam)session.get(Exam.class, examIdCheck);
            //map to object
            Query query = session.createQuery("FROM Appointment a WHERE a.studentId = :stuId");
            query.setParameter("stuId", studentCheck.getNetId());
            List<Appointment> list = query.list();
            size = list.size();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                Appointment appt = (Appointment) it.next();
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
        }catch (HibernateException var9) {
            if(tx != null) {
                tx.rollback();
            }
            var9.printStackTrace();
        } finally {
            session.close();
        }
        if(legalAppointment==size)
            return true;
        else
            return false;
    }

    public String getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public LocalDateTime getStartDateTime() {// date to
        Instant instant = Instant.ofEpochMilli(startDateTime.getTime());
        LocalDateTime res = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        return res;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        Instant instant = startDateTime.atZone(ZoneId.systemDefault()).toInstant();
        this.startDateTime = Date.from(instant);
    }



    public LocalDateTime getEndDateTime() {
        Instant instant = Instant.ofEpochMilli(endDateTime.getTime());
        LocalDateTime res = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        return res;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        Instant instant = endDateTime.atZone(ZoneId.systemDefault()).toInstant();
        this.endDateTime = Date.from(instant);
    }



    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
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

    // TODO SOME FIELD SHOULD NOT BE CHANGED
    public boolean update(Appointment appointment){
        if(!this.getAppointmentID().equals(appointment.getAppointmentID())){
            return false;
        }
        else{
            this.setMadeBy(appointment.getMadeBy());
            this.setExamId(appointment.getExamId());
            this.setStartDateTime(appointment.getStartDateTime());
            this.setEndDateTime(appointment.getEndDateTime());
            this.setStudentId(appointment.getStudentId());
            this.setSeat(appointment.getSeat());
            this.setIsAttend(appointment.isAttend());
            return true;
        }

    }
}
