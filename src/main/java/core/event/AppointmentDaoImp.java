package core.event;

import core.service.SessionManager;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class AppointmentDaoImp implements AppointmentDao {

    public AppointmentDaoImp() {

    }

    @Override
    public List findAllAppointment() {
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = session.beginTransaction();
        List appointments = session.createQuery("FROM Appointment").list();
        session.close();
        return appointments;
    }

    @Override
    public List findAllByStudent(String NetId) {
        List result;
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery
                ("FROM Appointment A WHERE A.studentId = :stuId");

        query.setParameter("stuId", NetId);
        tx.commit();
        result = query.list();
        session.close();
        return result;
    }

    @Override
    public Appointment findByAppointmentID(String AppointmentID) {
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("FROM Appointment A WHERE A.appointmentID = :appId");
        query.setParameter("appId", AppointmentID);
        tx.commit();
        Appointment result = (Appointment)query.uniqueResult();
        session.close();
        return result;
    }

    @Override
    public boolean insertAppointment(Appointment appointment) {
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(appointment);
            tx.commit();
        }
        catch (HibernateException he){
            if(tx != null){
                tx.rollback();
            }
            return false;
        } finally {
            session.close();
        }
        return  true;
    }

    @Override
    public boolean deleteAppointment(String appointmentId) {

        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            Query query = session.createQuery
                    ("delete from Appointment R where R.appointmentID = :appointmentID");
            query.setParameter("appointmentID", appointmentId);

            tx.commit();
        }
        catch (HibernateException he){
            if(tx != null){
                tx.rollback();
            }
            return false;
        } finally {
            session.close();
        }
        return  true;
    }

    @Override
    public boolean updateAppointment(Appointment appointment, String id){
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            Query query = session.createQuery
                    ("update Appointment A set A  = :A where A.appointmentID = :appointmentID");
            query.setParameter("A", appointment);
            query.setParameter("appointmentID", id);
            query.executeUpdate();
            tx.commit();
        }
        catch (HibernateException he){
            if(tx != null){
                tx.rollback();
            }
            return false;
        } finally {
            session.close();
        }
        return  true;
    }

    @Override
    public List<Appointment> findAllAppointmentsByTerm(Term term) {
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = null;
        List<Appointment> appointments = new ArrayList<>();
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from Appointment a where a.startDateTime >= :startDate and :endDate >= a.endDateTime");
            query.setTimestamp("endDate", Date.from(term.getTermEndDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
            query.setTimestamp("startDate", Date.from(term.getTermStartDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));

            // If query won't get any record from table, the result will be an empty list.
            appointments = query.list();
            tx.commit();
        } catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return appointments;
    }



}
