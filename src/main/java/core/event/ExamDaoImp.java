package core.event;

import core.service.SessionManager;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ExamDaoImp implements ExamDao {

    public ExamDaoImp() {
    }

    @Override
    public List getAllExams() {
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = null;
        List exams = null;
        try {
            tx = session.beginTransaction();
            exams = session.createQuery("FROM Exam ").list();
            tx.commit();
        } catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
            return exams;
        }
    }

    @Override
    public Exam findByExamId(String examId) {
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery
                ("FROM Exam E WHERE E.examId = :eId");
        query.setParameter("eId", examId);
        tx.commit();
        Exam result = (Exam)query.uniqueResult();
        session.close();
        return result;
    }

    @Override
    public List findByInstructorId(String instructorId) {
        List result;
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery
                ("FROM Exam E WHERE E.instructorId = :insId");

        query.setParameter("insId", instructorId);
        tx.commit();
        result = query.list();
        session.close();
        return result;
    }

    @Override
    public boolean addExam(Exam exam) {
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(exam);
            tx.commit();
        }catch (HibernateException he){
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
    public boolean updateExam(Exam exam, String id) {
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery
                    ("update Exam E set E  = :E where E.examId = :examId");
            query.setParameter("E", exam);
            query.setParameter("examId", id);
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
    public boolean deleteExam(Exam exam) {
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            Query query = session.createQuery
                    ("delete from Exam E where E.examId = :examId");
            query.setParameter("examId", exam.getExamId());

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
    public void listExamByApprovedRequest(String exId){//listReservationByApprovedRequest
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery
                    ("FROM Exam E WHERE E.examId = :examId");
            query.setParameter("examId", exId);
            tx.commit();
            Exam ex = (Exam)query.uniqueResult();

            System.out.println("----------------------------------------------------------------------");
            System.out.println("|  -Exam Id: " + ex.getExamId());
            System.out.println("|  -StartDateTime: " + ex.getStartDateTime());
            System.out.println("|  -EndDateTime: " + ex.getEndDateTime());
            System.out.println("|  -Exam Type: " + ex.getType());
            System.out.println("|  -Duration: " + ex.getDuration());
            System.out.println("|  -Attendence " + ex.getAttendance());
            System.out.println("----------------------------------------------------------------------");
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
