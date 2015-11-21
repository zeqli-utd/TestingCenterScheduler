package core.event;

import core.service.SessionManager;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@Repository
public class ReservationDaoImp implements ReservationDao {

    public ReservationDaoImp() {

    }

    /**
     * Get all reservation list
     * @return a list with all reservation in it
     */
    @Override
    public List<Reservation> findAll() {
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = null;
        List<Reservation> reservations = new List<Reservation>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<Reservation> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(Reservation reservation) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends Reservation> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends Reservation> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public Reservation get(int index) {
                return null;
            }

            @Override
            public Reservation set(int index, Reservation element) {
                return null;
            }

            @Override
            public void add(int index, Reservation element) {

            }

            @Override
            public Reservation remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<Reservation> listIterator() {
                return null;
            }

            @Override
            public ListIterator<Reservation> listIterator(int index) {
                return null;
            }

            @Override
            public List<Reservation> subList(int fromIndex, int toIndex) {
                return null;
            }
        };

        try{
            tx = session.beginTransaction();
            reservations = session.createQuery("FROM Reservation").list();
        }
        catch (HibernateException he){
            if(tx != null){
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return reservations;
    }

    /**
     * Find the reservation with the specific reservation id
     * @param id primary key of the reservation
     * @return the specific reservation
     */
    @Override
    public Reservation findByID(String id) {
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = null;
        Reservation result = new Reservation();
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery
                    ("FROM Reservation r WHERE r.reservationID = :reservationID");
            query.setParameter("reservationID", id);
            tx.commit();
            result = (Reservation) query.uniqueResult();
        }
        catch (HibernateException he){
            if(tx != null){
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return result;


    }

    /**
     * Find a list with the specific date
     * @param date specific date
     * @return all the reservation happens on that date
     */
    @Override
    public List<Reservation> findByDate(LocalDate date) {
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = null;
        List<Reservation> result = new List<Reservation>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<Reservation> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(Reservation reservation) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends Reservation> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends Reservation> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public Reservation get(int index) {
                return null;
            }

            @Override
            public Reservation set(int index, Reservation element) {
                return null;
            }

            @Override
            public void add(int index, Reservation element) {

            }

            @Override
            public Reservation remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<Reservation> listIterator() {
                return null;
            }

            @Override
            public ListIterator<Reservation> listIterator(int index) {
                return null;
            }

            @Override
            public List<Reservation> subList(int fromIndex, int toIndex) {
                return null;
            }
        };

        try{
            tx = session.beginTransaction();
            Query query = session.createQuery
                    ("FROM Reservation r WHERE r.startDateTime <= :startDate and r.endDateTime >= :endDate");

            query.setParameter("startDate", date);
            query.setParameter("endDate", date);

            tx.commit();
            result = query.list();
        }
        catch (HibernateException he){
            if(tx != null){
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return result;
    }

    /**
     * Find all the reservations that created by the specific instructor
     * @param InstructorID
     * @return
     */
    @Override
    public List<Reservation> findByInstructorId(String InstructorID) {
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = null;
        List<Reservation> result = new List<Reservation>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<Reservation> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(Reservation reservation) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends Reservation> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends Reservation> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public Reservation get(int index) {
                return null;
            }

            @Override
            public Reservation set(int index, Reservation element) {
                return null;
            }

            @Override
            public void add(int index, Reservation element) {

            }

            @Override
            public Reservation remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<Reservation> listIterator() {
                return null;
            }

            @Override
            public ListIterator<Reservation> listIterator(int index) {
                return null;
            }

            @Override
            public List<Reservation> subList(int fromIndex, int toIndex) {
                return null;
            }
        };

        try{
            tx = session.beginTransaction();
            Query query = session.createQuery
                    ("FROM Reservation r WHERE r.instructorId = :insId");

            query.setParameter("insId", InstructorID);
            tx.commit();
            result = query.list();
        }
        catch (HibernateException he){
            if(tx != null){
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public boolean insertReservation(Reservation reservation){
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(reservation);
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
            return false;
        } finally {
            session.close();
        }
        return true;
    }

    @Override
    public boolean deleteReservation(String reservationId){//need to throw exception
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery
                    ("delete from Reservation R where R.reservationID = :reservationID");
            query.setParameter("reservationID", reservationId);
            int ret = query.executeUpdate();// this int return the number of entities updated or deleted
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
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public void listAllReservationByInstructorId(String instructorId){
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Query query = session.createQuery
                    ("FROM Reservation r WHERE r.instructorId = :instructorId");
            query.setParameter("instructorId", instructorId);
            tx.commit();
            List result = query.list();
            for (Object aResult : result) {
                Reservation re = (Reservation) aResult;

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

        catch (HibernateException he){
            if(tx != null){
                tx.rollback();
            }
        } finally {
            session.close();
        }



    }

}
