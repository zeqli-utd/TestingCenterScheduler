package core.event.dao;

import core.event.*;
import core.service.SessionManager;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ExamDaoImp implements ExamDao {

    @Autowired
    private TestingCenterTimeSlotsDaoImp tctsImp;

    public ExamDaoImp() {
    }

    /**
     * Get All Exam Currently in Pending Status
     * @return a list of pending exam
     */
    public List getAllPending() {
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        List<Exam> list = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from Exam as e where e.examType = :type");
            query.setParameter("type", ExamStatusType.PENDING);
            list = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return list;
    }

    /**
     * Get All Exam Currently in Approved Status
     * @return a list of approved exam
     */
    public List getAllApproved() {
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        List<Exam> list = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from Exam as e where e.examType = :type");
            query.setParameter("type", ExamStatusType.APROVED);
            list = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return list;
    }

    /**
     * Get All Exam Currently in Denied Status
     * @return a list of denied exam
     */
    public List getAllDenied() {
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        List<Exam> list = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from Exam as e where e.examType = :type");
            query.setParameter("type", ExamStatusType.DENIED);
            list = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return list;
    }

    /**
     * This method retrieve all exams from database, including the AD HOC exam
     *
     * @return
     */
    @Override
    public List getAllExams() {
        Session session = SessionManager.getInstance().openSession();
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
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery
                ("FROM Exam E WHERE E.examId = :eId");
        query.setParameter("eId", examId);
        tx.commit();
        Exam result = (Exam) query.uniqueResult();
        session.close();
        return result;
    }

    @Override
    public List findByInstructorId(String instructorId) {
        List result;
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery
                ("FROM Exam E WHERE E.instructor = :insId");
        query.setParameter("insId", instructorId);
        tx.commit();
        result = query.list();
        session.close();
        return result;
    }

    @Override
    public boolean addExam(Exam exam) {
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(exam);
            tx.commit();
        } catch (HibernateException he) {
            if (tx != null) {
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
        Session session = SessionManager.getInstance().openSession();
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
    public boolean deleteExam(String examId) {
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            Query query = session.createQuery
                    ("delete from Exam E where E.examId = :examId");
            query.setParameter("examId", examId);

            tx.commit();
        } catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
            return false;
        } finally {
            session.close();
        }
        return true;
    }

    @Override
    public void listExamByApprovedRequest(String exId) {//listReservationByApprovedRequest
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery
                    ("FROM Exam E WHERE E.examId = :examId");
            query.setParameter("examId", exId);
            tx.commit();
            Exam ex = (Exam) query.uniqueResult();
        } catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }

    public boolean approveExam(String examId) {
        //need to add time slot after approving exam request
        tctsImp.insertTimeSlotsByExamId(examId);

        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Exam e = (Exam)session.get(Exam.class, examId);
            e.setStatusType(ExamStatusType.APROVED);
            session.merge(e);
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
}
