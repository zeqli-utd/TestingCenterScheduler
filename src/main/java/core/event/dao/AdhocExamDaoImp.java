package core.event.dao;

import core.event.AdhocExam;
import core.event.Exam;
import core.service.SessionManager;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdhocExamDaoImp implements AdhocExamDao{
    @Override
    public List<AdhocExam> getAllAdhocExams() {
        return null;
    }

    @Override
    public AdhocExam findByAdhocExamId(String examId) {
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        AdhocExam adhocExam = null;
        try {
            tx = session.beginTransaction();
            adhocExam = (AdhocExam)session.get(Exam.class, examId);
            tx.commit();
        }catch (HibernateException he){
            if(tx != null){
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return adhocExam;
    }

    @Override
    public List<AdhocExam> findByInstructorId(String instructorId) {
        return null;
    }

    @Override
    public boolean addExam(AdhocExam exam) {
        Session session = SessionManager.getInstance().openSession();
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
    public boolean updateExam(AdhocExam exam, String id) {
        return false;
    }

    @Override
    public boolean deleteExam(String examId) {
        return false;
    }
}
