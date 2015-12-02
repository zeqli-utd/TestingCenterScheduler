package core.event.dao;

import core.event.Course;
import core.service.SessionManager;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CourseDaoImp implements CourseDao {
    

    public CourseDaoImp(){}

    @Override
    public List getAllCourse() {
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        List<Course> courses = null;
        try {
            tx = session.beginTransaction();
            courses = session.createQuery("FROM Course ").list();
            tx.commit();
        }
        catch (HibernateException he){
            if(tx != null){
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return courses;
    }

    @Override
    public Course findByCourseId(String courseId) {
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery
                ("FROM Course C WHERE C.courseId = :coId");
        query.setParameter("coId", courseId);
        tx.commit();
        Course result = (Course)query.uniqueResult();
        session.close();
        return result;
    }

    @Override
    public boolean addCourse(Course course) {
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(course);
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
    public boolean updateCourse(Course course, String id) {
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            Query query = session.createQuery
                    ("update Course C set C  = :C where C.courseId = :courseId");
            query.setParameter("C", course);
            query.setParameter("courseId", id);
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
    public boolean deleteCourse(String courseId) {
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery
                    ("delete from Course C where C.courseId = :courseId");
            query.setParameter
                    ("courseId", courseId);
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
    public boolean deleteCoursesByTerm(int termId){
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery
                    ("delete from Course as c where c.term = :termId");
            query.setParameter("termId", termId);
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
