package core.user;

import core.event.Appointment;
import core.service.SessionManager;
import org.apache.log4j.Logger;
import org.hibernate.*;
import test.Log4J;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Entity
@Table(name = "Administrator")
public class Administrator extends UserType {

    public static final Logger log = Logger.getLogger(Log4J.class);

//    @Basic(optional = false)
//    public final static Authorization authLevel = Authorization.ADMINISTRATOR;

    // Empty Constructor for Hibernate
    public Administrator() {
    }

    public Administrator(String netId, String pwd, String firstName, String lastName, String email) {
        this.netId = netId;
        //this.password = pwd;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }


    // The system displays all appointments and the number of available
    // seats at the current time or a specified other time.

    public List listAllAppointments(LocalDateTime ldt){

        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        List appointments = null;
        try {
            tx = session.beginTransaction();

            // USE SQL script
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String sql = "select * from appointment where '" + ldt.format(formatter) + "' > START_TIME AND '" + ldt.format(formatter) + "' < END_TIME ";
            log.info("---------- listAllAppointments(LocalDateTime ldt) ----------");
            log.info("- SQL script = " + sql);



            Query query = session.createQuery("from Appointment a where  :startDate <= a.startDateTime and a.endDateTime <= :endDate");
            query.setTimestamp("startDate", Date.from(ldt.toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
            query.setTimestamp("endDate", Date.from(ldt.toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
            appointments = query.list();

            Iterator it = appointments.iterator();
            while(it.hasNext()){
                Appointment appointment = (Appointment) it.next();
                log.info("---------- listAllAppointments(LocalDateTime ldt) ----------");
                log.info("|  -AppointmentID: " + appointment.getAppointmentID());
                log.info("|  -EmailId: " + appointment.getExamId());
                log.info("|  -MadeBy: " + appointment.getMadeBy() );
                log.info("|  -StartDateTime: " + appointment.getStartDateTime());
                log.info("|  -EndDateTime: " + appointment.getEndDateTime());
                log.info("|  -StudentId: " + appointment.getStudentId());
                log.info("|  -Seat: " + appointment.getSeat());
                log.info("|  -IsAttended: " + appointment.isAttend());
            }

            tx.commit();
            session.close();
        }catch (HibernateException he){
            he.printStackTrace();
            if(tx != null){
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return appointments;
    }

    public List listAllAppointments(){
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        List appointments = null;
        try {
            tx = session.beginTransaction();

            // USE SQL script
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            log.info("---------- listAllAppointments() ----------");
            Query query = session.createQuery("from Appointment");
            appointments = query.list();

            Iterator it = appointments.iterator();
            while(it.hasNext()){
                Appointment appointment = (Appointment) it.next();
                log.info("---------- listAllAppointments() ----------");
                log.info("|  -AppointmentID: " + appointment.getAppointmentID());
                log.info("|  -EmailId: " + appointment.getExamId());
                log.info("|  -MadeBy: " + appointment.getMadeBy() );
                log.info("|  -StartDateTime: " + appointment.getStartDateTime());
                log.info("|  -EndDateTime: " + appointment.getEndDateTime());
                log.info("|  -StudentId: " + appointment.getStudentId());
                log.info("|  -Seat: " + appointment.getSeat());
                log.info("|  -IsAttended: " + appointment.isAttend());
            }

            tx.commit();
            session.close();
        }catch (HibernateException he){
            he.printStackTrace();
            if(tx != null){
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return appointments;
    }


    // 1. Delete Appointment
    // 2. Release Seats
    public void cancelAppointment(String apptId){
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Appointment appt = (Appointment)session.get(Appointment.class, apptId);


            log.info("---------- CancelAppointment(String apptId)----------");
            log.info("|  -AppointmentID: " + appt.getAppointmentID());
            log.info("|  -EmailId: " + appt.getExamId());
            log.info("|  -MadeBy: " + appt.getMadeBy() );
            log.info("|  -StartDateTime: " + appt.getStartDateTime());
            log.info("|  -EndDateTime: " + appt.getEndDateTime());
            log.info("|  -StudentId: " + appt.getStudentId());
            log.info("|  -Seat: " + appt.getSeat());
            log.info("|  -IsAttended: " + appt.isAttend());

            session.delete(appt);
            tx.commit();
            session.close();
        }catch (HibernateException he){
            he.printStackTrace();
            if(tx != null){
                tx.rollback();
            }
        } finally {
            session.close();
        }
        // TestingCenterInfo.releaseSeat(appt)
    }

    // GET APPOINTMENT
    public Appointment getAppointment(String apptID){
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        Appointment appt = null;
        try {
            tx = session.beginTransaction();
            appt = (Appointment)session.get(Appointment.class, apptID);


            log.info("---------- getAppointment(String apptId)----------");
            log.info("|  -Appointment Id: " + appt.getAppointmentID());
            log.info("|  -Start Time: " + appt.getStartDateTime());
            log.info("|  -End Time: " + appt.getEndDateTime());
            log.info("|  -Student Name" + appt.getStudentId());

            tx.commit();
            session.close();
        }catch (HibernateException he){
            he.printStackTrace();
            if(tx != null){
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return appt;
    }

    // Update Edited Appointment
    public void updateAppointment(Appointment appt){
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(appt);

            log.info("---------- updateAppointment(Appointment apptId)----------");
            log.info("|  -AppointmentID: " + appt.getAppointmentID());
            log.info("|  -EmailId: " + appt.getExamId());
            log.info("|  -MadeBy: " + appt.getMadeBy());
            log.info("|  -StartDateTime: " + appt.getStartDateTime());
            log.info("|  -EndDateTime: " + appt.getEndDateTime());
            log.info("|  -StudentId: " + appt.getStudentId());
            log.info("|  -Seat: " + appt.getSeat());
            log.info("|  -IsAttended: " + appt.isAttend());

            tx.commit();
            session.close();
        }catch (HibernateException he){
            he.printStackTrace();
            if(tx != null){
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }

    /**
     * Checkin a student for an appointment.
     * The system records that the student kept the appointment and
     * displays the student seat assignment. (Ideally, students would check in
     * by swiping their ID card, but that is beyond the scope of this course project.)
     * @param netId
     */
    public String checkInStudent(String examId, String netId){
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        String assignedSeat = "N/A";
        Appointment appt = new Appointment();
        try {
            tx = session.beginTransaction();

            String sql = "from Appointment A where A.examId  = '" + examId +
                    "' and A.studentId = '" + netId + "'";

            Query query = session.createQuery(sql);
            List result = query.list();
            if(result!=null){
                appt = (Appointment)result.get(0);
                assignedSeat = appt.getSeat();
                appt.setIsAttend(true);
                session.save(appt);
                log.info("********** Checkin Student Success!!! **********");
                log.info("| -Student NetId: " + netId);
                log.info("| -Assigned Seat: " + assignedSeat);
                log.info("********** Checkin Student Success!!! **********");
            }
            else {
                log.info("********** Checkin Student Failed **********");
            }

            tx.commit();
            session.close();
        }catch (HibernateException he){
            log.error("Checkin Student Fail");
            he.printStackTrace();
            if(tx != null){
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return assignedSeat;

    }

    public boolean update(Administrator admin){
        if(this.netId != admin.getNetId()){
            return false;
        }
        else{
            this.setFirstName(admin.getFirstName());
            this.setLastName(admin.getLastName());
            this.setPassword(admin.getPassword());
            this.setEmail(admin.getEmail());
            return true;
        }

    }




    //static final authLevel is already initialized
    /**
    static {
        authLevel = Authorization.ADMINISTRATOR;
    }*/
}
