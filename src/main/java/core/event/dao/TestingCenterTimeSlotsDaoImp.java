package core.event.dao;

import core.event.Appointment;
import core.event.Exam;
import core.event.Slots;
import core.event.TestingCenterTimeSlots;
import core.service.SessionManager;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TestingCenterTimeSlotsDaoImp implements TestingCenterTimeSlotsDao {

    @Override
    public List<TestingCenterTimeSlots> findAllTimeSlots(){
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        List tscs = null;
        try {
            tx = session.beginTransaction();
            tscs = session.createQuery("FROM TestingCenterTimeSlots").list();
            tx.commit();
        }
        catch (HibernateException he){
            if(tx != null){
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return tscs;
    }


    public List<TestingCenterTimeSlots> findAllAvailableTimeSlots(){
        List<TestingCenterTimeSlots> list = findAllTimeSlots();
        for (TestingCenterTimeSlots tcts: list){
            if(tcts.getOccupiedNum() == tcts.getNumSeat())
                list.remove(tcts);
        }
        return list;
    }

    @Override
    public List<TestingCenterTimeSlots> findAllTimeSlotsByExamId(String examId){
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        List result = null;
        try {
            Query query = session.createQuery
                    ("FROM core.event.TestingCenterTimeSlots ts WHERE ts.examId = :exId");
            query.setParameter("exId", examId);
            result = query.list();
            tx.commit();
        }
        catch (HibernateException he){
            if(tx != null){
                tx.rollback();
            }
        } finally {
            session.close();
        }
        session.close();
        return result;
    }

    @Override
    public TestingCenterTimeSlots findTimeSlotById(String timeSlotId){
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        TestingCenterTimeSlots result = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM TestingCenterTimeSlots ts WHERE ts.timeSlotId = :tsId");
            query.setParameter("tsId", timeSlotId);
            result = (TestingCenterTimeSlots)query.uniqueResult();
           // List a = query.list();
            tx.commit();
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

    @Override
    public boolean insertTimeSlots(List<TestingCenterTimeSlots> listTimeSlots){
        boolean result = true;
        for (TestingCenterTimeSlots t: listTimeSlots)
            if(insertTimeSlot(t) == false)
                result = false;
        //result is false doesn't mean it failed
        //it means it didn't import the complete list
        return result;
    }

    @Override
    public boolean insertTimeSlotsByExamId(String examId){
        ExamDaoImp examImp = new ExamDaoImp();
        Exam exam = examImp.findByExamId(examId);
        Slots slots = new Slots(exam);
        List<TestingCenterTimeSlots> timeSlots = slots.generateTimeSlots();
        TestingCenterTimeSlots tctsIter;
        int i = 0;
        if(timeSlots.size() <= 0)
            return false;
        while(i < timeSlots.size()){
            tctsIter = timeSlots.get(i);
            insertTimeSlot(tctsIter);
            i++;
        }
        return true;
    }

    @Override
    public boolean deleteTimeSlot(String timeSlotId){
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            Query query = session.createQuery
                    ("delete from TestingCenterTimeSlots ts where ts.timeSlotId = :timeSlotId");
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
        return true;
    }
    //need to fix
    public boolean updateTimeSlot(TestingCenterTimeSlots tcts){
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(tcts);
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
