package core.user;

import core.event.Appointment;
import core.service.SessionManager;
import org.hibernate.*;

import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Entity
public class Administrator extends UserType {


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

            Query query = session.createQuery("from Appointment a where  :startDate <= a.startDateTime and a.endDateTime <= :endDate");
            query.setTimestamp("startDate", Date.from(ldt.toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
            query.setTimestamp("endDate", Date.from(ldt.toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
            appointments = query.list();

            Iterator it = appointments.iterator();
            while(it.hasNext()){
                Appointment appointment = (Appointment) it.next();
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
            Query query = session.createQuery("from Appointment");
            appointments = query.list();

            Iterator it = appointments.iterator();
            while(it.hasNext()){
                Appointment appointment = (Appointment) it.next();
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
            }
            else {
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
        return assignedSeat;

    }

    public boolean update(Administrator admin){
        if(this.netId != admin.getNetId()){
            return false;
        }
        else{
            this.setFirstName(admin.getFirstName());
            this.setLastName(admin.getLastName());
            //this.setPassword(admin.getPassword());
            this.setEmail(admin.getEmail());
            return true;
        }

    }
}
