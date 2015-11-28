package core.event.dao;

import core.event.*;
import core.service.SessionManager;
import core.service.TestingCenterInfoRetrieval;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = session.beginTransaction();
        List appointments = session.createQuery("FROM Appointment").list();
        session.close();
        return appointments;
    }

    @Override
    public List findAllByStudent(String NetId) {
        List result;
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery
                ("FROM Appointment A WHERE A.studentId = :stuId");
        query.setParameter("stuId", NetId);
        tx.commit();
        result = query.list();
        session.close();
        return result;
    }

    public List findAllAppointmentByTime(LocalDateTime time){
        ArrayList<Appointment> result = new ArrayList<Appointment>();
        List allAppointment = findAllAppointment();
        Appointment appointmentIter = new Appointment();
        TestingCenterInfoRetrieval info = new TestingCenterInfoRetrieval();
        int gap = info.findByTerm(info.getCurrentTerm()).getGap();
        for(int i = 0; i < allAppointment.size(); i++) {
            appointmentIter = (Appointment)allAppointment.get(i);
            if ( (time.minusMinutes(gap).isBefore(appointmentIter.getEndDateTime()))
                    &&  (
                        (time.isAfter(appointmentIter.getStartDateTime()))
                            ||
                                time.isEqual(appointmentIter.getStartDateTime())
                        )
                    ){
                result.add(appointmentIter);
            }
        }
        return result;
    }

    @Override
    public boolean insertAppointment(Appointment appointment) {
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            TestingCenterTimeSlotsDaoImp tscsImp = new TestingCenterTimeSlotsDaoImp();
            LocalDateTime begin = appointment.getStartDateTime();
            TestingCenterTimeSlots tscs = tscsImp.findTimeSlotById(Integer.toString(begin.getDayOfYear()) +
                    Integer.toString(begin.getHour()) + Integer.toString(begin.getMinute()));
            tscsImp.insertTimeSlot(tscs);
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
        return true;
    }

    @Override
    public boolean deleteAppointment(String appointmentId) {
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            TestingCenterTimeSlotsDaoImp tscsImp = new TestingCenterTimeSlotsDaoImp();
            Appointment appt = findAppointmentById(appointmentId);
            LocalDateTime begin = appt.getStartDateTime();
            TestingCenterTimeSlots tscs = tscsImp.findTimeSlotById(Integer.toString(begin.getDayOfYear()) +
                    Integer.toString(begin.getHour()) + Integer.toString(begin.getMinute()));
            tscs.releaseSeat(appt);
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
        Session session = SessionManager.getInstance().openSession();
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
        Session session = SessionManager.getInstance().openSession();
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

    @Override
    public Appointment findAppointmentById(String AppointmentID) {
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("FROM Appointment A WHERE A.appointmentID = :appId");
        query.setParameter("appId", AppointmentID);
        tx.commit();
        Appointment result = (Appointment)query.uniqueResult();
        session.close();
        return result;
    }
}
