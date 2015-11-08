package core.event;

import core.service.SessionManager;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.springframework.stereotype.Repository;
import test.Log4J;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ReservationDaoImp implements ReservationDao {

    private static final Logger log = Logger.getLogger(Log4J.class);

    public ReservationDaoImp(){

    }

    @Override
    public List<Reservation> findAll() {
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = null;
        tx = session.beginTransaction();
        List<Reservation> reservations = session.createQuery("FROM Reservation").list();
        session.close();
        return reservations;
    }

    @Override
    public Reservation findByID(String id) {
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery
                ("FROM Reservation r WHERE r.reservationID = :reservationID");
        query.setParameter("reservationID", id);
        tx.commit();
        Reservation result = (Reservation)query.uniqueResult();
        session.close();
        return result;
    }

    @Override
    public List<Reservation> findByDate(LocalDate date) {
        ArrayList<Reservation> result;
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery
                ("FROM Reservation r WHERE r.startDateTime <= :startDate and r.endDateTime >= :endDate");

        query.setParameter("startDate", date);
        query.setParameter("endDate", date);

        tx.commit();
        result = (ArrayList<Reservation>)query.list();
        session.close();
        return result;
    }

    @Override
    public List<Reservation> findByInstructorId(String InstructorID) {
        ArrayList<Reservation> result;
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery
                ("FROM Reservation r WHERE r.instructorId = :insId");

        query.setParameter("insId", InstructorID);
        tx.commit();
        result = (ArrayList<Reservation>)query.list();
        session.close();
        return result;
    }

    @Override
    public boolean insertReservation(Reservation reservation){
        //how to know which table we add in
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = null;
        try {
            System.out.println("CAO1");
            tx = session.beginTransaction();
            System.out.println("CAO2");
            session.save(reservation);
            System.out.println("CAO3");
            tx.commit();
        }
        catch (HibernateException he){
            if(tx != null){
                tx.rollback();
            }
            log.error("Error with addExam ", he);
            return false;
        } finally {
            session.close();
        }
        return  true;
    }

    @Override
    public boolean updateReservation(Reservation newReservation, String id){
    // same Id as the old reservation, but we have set the new data in this new one
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery
                    ("update Reservation R set R  = :R where R.reservationID = :reservationID");
            query.setParameter("R", newReservation);
            query.setParameter("reservationID", id);
            query.executeUpdate();
            tx.commit();
        }
        catch (HibernateException he){
            if(tx != null){
                tx.rollback();
            }
            log.error("Error with addExam ", he);
            return false;
        } finally {
            session.close();
        }
        return true;
    }

    @Override
    public boolean deleteReservation(Reservation reservation){//need to throw exception
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery
                    ("delete from Reservation R where R.reservationID = :reservationID");
            query.setParameter("reservationID", reservation.getReservationID());
            int ret = query.executeUpdate();// this int return the number of entities updated or deleted
            tx.commit();
        }
        catch (HibernateException he){
            if(tx != null){
                tx.rollback();
            }
            log.error("Error with addExam ", he);
            return false;
        } finally {
            session.close();
        }
        return true;
    }

    @Override
    public boolean setType(String reservationId, String type) {
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            Query query = session.createQuery
                    ("update Reservation R set R.types = :tp where R.reservationID = :reservationID");

            query.setParameter("tp", type);
            query.setParameter("reservationID", reservationId);
            query.executeUpdate();
            tx.commit();
        }
        catch (HibernateException he){
            if(tx != null){
                tx.rollback();
            }
            log.error("Error with addExam ", he);
            return false;
        } finally {
            session.close();
        }
        return true;
    }

    @Override
    public String getTypeById(String reservationId) {
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = null;
        String result = "";
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery
                    ("select types from Reservation R where R.reservationID = :reservationID");

            query.setParameter("reservationID", reservationId);

            tx.commit();
            result = (String)query.uniqueResult();
        }
        catch (HibernateException he){
            if(tx != null){
                tx.rollback();
            }
            log.error("Error with addExam ", he);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public boolean setStatus(String reservationId, String Status) {
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery
                    ("update Reservation R set R.status = :status where R.reservationID = :reservationID");
            query.setParameter("status", Status);
            query.setParameter("reservationID", reservationId);
            query.executeUpdate();
            tx.commit();
        }
        catch (HibernateException he){
            if(tx != null){
                tx.rollback();
            }
            log.error("Error with addExam ", he);
            return false;
        } finally {
            session.close();
        }
        return true;
    }

    @Override
    public String getStatusById(String reservationId) {
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = null;
        String result = "";
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery
                    ("select status from Reservation R where R.reservationID = :reservationID");

            query.setParameter("reservationID", reservationId);
            tx.commit();
            result = (String)query.uniqueResult();
        }
        catch (HibernateException he){
            if(tx != null){
                tx.rollback();
            }
            log.error("Error with addExam ", he);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public void listAllReservationByInstructorId(String instructorId){
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery
                ("FROM Reservation r WHERE r.instructorId = :instructorId");
        query.setParameter("instructorId", instructorId);
        tx.commit();
        List result = query.list();
        session.close();

        Iterator resultIter = result.iterator();

        while(resultIter.hasNext()){
            Reservation re = (Reservation)resultIter.next();

            System.out.println("|  -Reservation Id: " + re.getReservationID());
            System.out.println("|  -StartDateTime: " + re.getStartDateTime());
            System.out.println("|  -EndDateTime: " + re.getEndDateTime());
            System.out.println("|  -Instructor Id: " + re.getInstructorId());
            System.out.println("|  -Term: " + re.getTerms());
            System.out.println("|  -Status " + re.getStatus());
            System.out.println("|  -Type: " + re.getType());
            System.out.println("----------------------------------------------------------------------");
        }
    }

}
