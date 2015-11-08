package core.event;

import core.service.SessionManager;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by eson_wang on 11/2/15.
 */
@Repository
public class CourseDaoImp implements CourseDao {
    private static final Logger log = Logger.getLogger(CourseDaoImp.class);
    List<Course> courses;

    public CourseDaoImp(){}

    @Override
    public List<Course> getAllCourse() {
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = session.beginTransaction();
        courses = session.createQuery("FROM Course ").list();
        session.close();
        return courses;
    }

    @Override
    public Course findByCourseId(String courseId) {
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("FROM Course C WHERE C.courseId = :coId");
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
            log.error("Error with addExam ", he);
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

            Query query = session.createQuery("update Course C set C  = :C where C.courseId = :courseId");
            query.setParameter("C", course);
            query.setParameter("courseId", id);
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
        return  true;
    }

    @Override
    public boolean deleteCourse(Course course) {
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("delete from Course R where R.courseId = :courseId");
            query.setParameter("courseId", course.getCourseId());
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
}
