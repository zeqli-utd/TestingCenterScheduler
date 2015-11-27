package core.service;

import core.event.Term;
import core.event.TestingCenterInfo;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class TestingCenterInfoRetrieval {

    public TestingCenterInfo newTermTestingCenterInfo(Term term) {
        TestingCenterInfo testingCenterInfo = new TestingCenterInfo();
        //TODO
        return testingCenterInfo;
    }

    public TestingCenterInfo findByTerm(Term term) {
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery
                ("FROM TestingCenterInfo T WHERE T.term = :tId");
        query.setParameter("tId", term);
        tx.commit();
        TestingCenterInfo result = (TestingCenterInfo)query.uniqueResult();
        session.close();
        return result;
    }

    public Term getCurrentTerm(){
        LocalDate now = LocalDate.now();
        Term result = null;
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery
                    ("FROM Term T WHERE T.termStartDate <= :date AND  :date <= T.termEndDate");
            query.setParameter("date", now);
            tx.commit();
            result = (Term)query.uniqueResult();
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

    public Term getTermByDay(LocalDateTime day){
        LocalDate now = day.toLocalDate();
        Term result = null;
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery
                    ("FROM Term T WHERE T.termStartDate <= :date AND  :date <= T.termEndDate");
            query.setParameter("date", now);
            tx.commit();
            result = (Term)query.uniqueResult();
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

    public boolean updateField(Term term, String fieldName, Object value) {
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        boolean result = false;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery
                    ("FROM TestingCenterInfo T WHERE T.term = :termId");
            query.setParameter("termId", term);
            tx.commit();
            TestingCenterInfo tc = (TestingCenterInfo) query.uniqueResult();
            switch (fieldName) {
                case "numSeats":
                    tc.setNumSeats((int) value);
                    result = true;
                    return true;
                case "numSetAsideSeats":
                    tc.setNumSetAsideSeats((int) value);
                    result = true;
                    return true;
                case "open":
                    tc.setOpen((LocalTime) value);
                    result = true;
                    return true;
                case "close":
                    tc.setClose((LocalTime) value);
                    result = true;
                    return true;
                //need i to edit value for this field
//            case "closeDateRanges":     testingCenterInfo.setCloseDateRanges((List<LocalDate[]>)value);
//                                        return true;
                //also need i to edit value for this field
//            case "reserveRanges":       testingCenterInfo.setReserveRanges((List<LocalDateTime[]>)value);
//                                        return true;
                case "gap":
                    tc.setGap((int) value);
                    result = true;
                    return true;
                case "reminderInterval":
                    tc.setReminderInterval((int) value);
                    result = true;
                    return true;
            }
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
     * add function for both closeDateRanges and reserveRanges
     * @param term
     * @param fieldName: closeDateRanges and reserveRanges
     * @param dates
     * @return
     */
    public boolean addDates(Term term, String fieldName, Object dates){
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        boolean result = false;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery
                    ("FROM TestingCenterInfo T WHERE T.term = :termId");
            query.setParameter("termId", term);
            tx.commit();
            TestingCenterInfo tc = (TestingCenterInfo)query.uniqueResult();
            switch (fieldName){
                case "closeDateRanges":
                    tc.getCloseDateRanges().add((LocalDate[]) dates);
                    result = true;
                    break;
                case "reserveRanges":
                    tc.getReserveRanges().add((LocalDateTime[]) dates);
                    result = true;
                    break;
            }
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
     * edit function for both closeDateRanges and reserveRanges
     * @param term
     * @param fieldName: closeDateRanges and reserveRanges
     * @param i: index of the close dates that should be edited
     * @param dates
     * @return
     */
    public boolean editDates(Term term, String fieldName, int i, Object dates){
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        boolean result = false;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery
                    ("FROM TestingCenterInfo T WHERE T.term = :termId");
            query.setParameter("termId", term);
            tx.commit();
            TestingCenterInfo tc = (TestingCenterInfo)query.uniqueResult();
            switch (fieldName){
                case "closeDateRanges":
                    tc.getCloseDateRanges().set(i, (LocalDate[])dates);
                    result = true;
                    break;
                case "reserveRanges":
                    tc.getReserveRanges().set(i, (LocalDateTime[]) dates);
                    result = true;
                    break;
            }
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
     * delete functino for closeDateRanges and reserveRanges
     * @param term
     * @param i: index of the close dates that should be removed
     * @return
     */
    public boolean deleteCloseDates(Term term, String fieldName, int i){
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        boolean result = false;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery
                    ("FROM TestingCenterInfo T WHERE T.term = :termId");
            query.setParameter("termId", term);
            tx.commit();
            TestingCenterInfo tc = (TestingCenterInfo)query.uniqueResult();
            switch (fieldName){
                case "closeDateRanges":
                    tc.getCloseDateRanges().remove(i);
                    result = true;
                    break;
                case "reserveRanges":
                    tc.getReserveRanges().remove(i);
                    result = true;
                    break;
            }
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
}
