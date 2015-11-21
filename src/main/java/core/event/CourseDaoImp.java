package core.event;

import core.service.SessionManager;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CourseDaoImp implements CourseDao {
    List courses;

    public CourseDaoImp(){}

    @Override
    public List getAllCourse() {
        Session session = SessionManager.getInstance().getOpenSession();
        courses = session.createQuery("FROM Course ").list();
        session.close();
        return courses;
    }

    @Override
    public Course findByCourseId(String courseId) {
        Session session = SessionManager.getInstance().getOpenSession();
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
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(course);
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
        Session session = SessionManager.getInstance().getOpenSession();
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
        Session session = SessionManager.getInstance().getOpenSession();
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
}
