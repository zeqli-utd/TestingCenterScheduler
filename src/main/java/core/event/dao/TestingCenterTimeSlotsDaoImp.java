package core.event.dao;

import core.event.Appointment;
import core.event.TestingCenterTimeSlots;
import core.service.SessionManager;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TestingCenterTimeSlotsDaoImp implements TestingCenterTimeSlotsDao {
    public List<TestingCenterTimeSlots> findAllTimeSlots(){
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = session.beginTransaction();
        List appointments = session.createQuery("FROM timeSlots").list();
        session.close();
        return appointments;
    }

    public TestingCenterTimeSlots findTimeSlotById(String timeSlotId){
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("FROM timeSlots ts WHERE ts.timeSlotId = :tsId");
        query.setParameter("apptsId", timeSlotId);
        tx.commit();
        TestingCenterTimeSlots result = (TestingCenterTimeSlots)query.uniqueResult();
        session.close();
        return result;
    }

    public boolean insertTimeSlot(TestingCenterTimeSlots timeSlots){
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(timeSlots);
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

    public boolean deleteTimeSlot(String timeSlotId){
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            Query query = session.createQuery
                    ("delete from timeSlots ts where ts.timeSlotId = :timeSlotId");
            query.setParameter("timeSlotId", timeSlotId);
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
}
