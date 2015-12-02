package core.event.dao;

import core.event.Exam;
import core.event.ExamStatusType;
import core.event.Slots;
import core.event.TestingCenterInfo;
import core.service.SessionManager;
import core.service.TestingCenterInfoRetrieval;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ExamDaoImp implements ExamDao {

    @Autowired
    private TestingCenterInfoRetrieval tciRe;



    public ExamDaoImp() {
    }

    /**
     * Get All Exam Currently in Pending Status
     *
     * @return a list of pending exam
     */
    public List getAllPending() {
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        List<Exam> list = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from Exam as e where e.statusType = :type");
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
     *
     * @return a list of approved exam
     */
    public List getAllApproved() {
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        List<Exam> list = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from Exam as e where e.statusType = :type");
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
     *
     * @return a list of denied exam
     */
    public List getAllDenied() {
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        List<Exam> list = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from Exam as e where e.statusType = :type");
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
        Transaction tx = null;
        Exam exam = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery
                    ("FROM Exam E WHERE E.examId = :eId");
            query.setParameter("eId", examId);
            exam = (Exam) query.uniqueResult();
            tx.commit();
        } catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
            return exam;
        }
    }

    @Override
    public List<Exam> findByInstructorId(String instructorId) {
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        List<Exam> examList = new ArrayList<>();
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery
                    ("FROM Exam E WHERE E.instructorId = :insId");
            query.setParameter("insId", instructorId);
            examList = query.list();
            tx.commit();
        } catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
            return examList;
        }
    }

    /**
     * Check if the exam if legal to schedule
     *
     * @param exam
     * @return
     */
    public boolean checkLegalExam(Exam exam) {
        TestingCenterInfo tci = tciRe.findByTerm(exam.getTerm());
        LocalTime open = tci.getOpen();
        LocalTime close = tci.getClose();
        int gap = tci.getGap();
        LocalDateTime startDateTime = exam.getStartDateTime();
        LocalDateTime endDateTime = exam.getEndDateTime();

        //check exam time between open hour and close hour
        LocalTime startTime = startDateTime.toLocalTime();
        LocalTime endTime = endDateTime.toLocalTime();
        if (startTime.isBefore(open) || !startTime.isBefore(close) ||
                endTime.isAfter(close) || !endTime.isAfter(open))
            return false;

        //check exam time between reserved dateTimes
        //List<ETSTestTimeRangeTuple> reservedRanges = tci.getReserveRanges();
        //for (ETSTestTimeRangeTuple ets: reservedRanges){
        //    LocalDateTime begin = ets.getTestDateTimeFrom();
        //    LocalDateTime end = ets.getTeseDateTimeTo();
        //    if ( !(startDateTime.isBefore(end)) ||
        //            !(endDateTime.isAfter(begin)) ) {
        //        //continue;
        //    }
        //    else{
        //        return false;
        //    }
        //}
        //
        ////check exam time between closed dates
        //LocalDate startDate = startDateTime.toLocalDate();
        //LocalDate endDate = endDateTime.toLocalDate();
        //List<CloseDateRangeTuple> closedRanges = tci.getCloseDateRanges();
        //for (CloseDateRangeTuple cTuple: closedRanges){
        //    LocalDate begin = cTuple.getCloseDateFrom();
        //    LocalDate end = cTuple.getCloseDateTo();
        //    if ( startDate.isAfter(end) || endDate.isBefore(begin) ) {
        //        //continue;
        //    }
        //    else{
        //        return false;
        //    }
        //}
        return true;

    }

    /**
     * Add Exam with s
     *
     * @param exam
     * @return
     */
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

    /**
     * Add Exam without Schedubility Check
     *
     * @param exam
     * @return
     */
    @Override
    public boolean insertExam(Exam exam) {
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

    public boolean updateExam(Exam examToUpdate){
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.merge(examToUpdate);
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
    public List<Exam> getAllAvailableExamsToStudent(String studentId) {
        return null;
    }
}
