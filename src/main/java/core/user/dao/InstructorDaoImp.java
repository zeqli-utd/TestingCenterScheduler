package core.user.dao;

import core.service.SessionManager;
import core.user.Instructor;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InstructorDaoImp implements InstructorDao {

    List<Instructor> instructors;

    public InstructorDaoImp() {
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        tx = session.beginTransaction();
        instructors = session.createQuery("FROM Instructor ").list();
        session.close();
    }
    @Override
    public List<Instructor> findAll() {
        return instructors;
    }

    @Override
    public Instructor findByNetID(String id) {
        return instructors.get(Integer.parseInt(id));
    }

    @Override
    public boolean addInstructor(Instructor instructor) {
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        try {

            tx = session.beginTransaction();
            session.save(instructor);
            session.getTransaction().commit();
        }
        catch (HibernateException he){
            if(tx != null){
                tx.rollback();
            }
            return false;
        }finally {
            session.close();
        }
        return true;
    }

    @Override
    public boolean updateInstructor(Instructor instructor, String id) {
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("update Instructor I set I  = :I where I.netId = :netId");
            query.setParameter("I", instructor);
            query.setParameter("netId", id);

            int ret = query.executeUpdate();
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
    public boolean deleteInstructor(Instructor instructor) {
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("delete from Instructor I where I.netId = :id");
            query.setParameter("id", instructor.getNetId());
            int ret = query.executeUpdate();// this int return the number of entities updated or deleted
            tx.commit();
            session.close();
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
