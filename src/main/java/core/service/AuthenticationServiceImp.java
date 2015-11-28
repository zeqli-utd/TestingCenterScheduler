package core.service;

import core.user.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;

@Repository
public class AuthenticationServiceImp implements AuthenticationService{
    /*      SessionFactory sessionFactory = sessionManager.createSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(e);                    //save object using hibernate
            session.getTransaction().commit();*/

   // private static SessionManager sessionManager = new SessionManager();
    //SessionFactory sessionFactory = sessionManager.createSessionFactory();

    //UserType AllUser = new UserType();

    @Override
    public boolean registeredUserId(String userId) {
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        boolean result = false;
        try {
            tx = session.beginTransaction();

            List allUserId = session.createQuery("SELECT netId FROM UserType").list();

            result = allUserId.contains(userId);
        }
        catch (HibernateException he){
            if(tx != null){
                tx.rollback();
            }
            return false;
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public boolean userMatchPassword(String userId, String password) {
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        //boolean result = false;
        try {
            tx = session.beginTransaction();

            List allUser = session.createQuery("FROM User").list();
            Iterator userIter = allUser.iterator();

            while(userIter.hasNext()){
                User user = (User)userIter.next();
                if(user.getNetId().equals(userId)){
                    if(user.getPassword().equals(password)){
                        return true;
                    }
                    return false;
                }
            }
        }
        catch (HibernateException he){
            if(tx != null){
                tx.rollback();
            }
            return false;
        } finally {
            session.close();
        }
        return false;
    }

    @Override
    public Authorization login(String userId, String password) {
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            List allAdministrator = session.createQuery("FROM Administrator").list();
            List allInstructor = session.createQuery("FROM Instructor").list();
            //List allStudent = session.createQuery("FROM Student").list();

            Iterator AdministratorIter = allAdministrator.iterator();
            Iterator InstructorIter = allInstructor.iterator();
            //Iterator StudentIter = allStudent.iterator();

            while(AdministratorIter.hasNext()){
                if(AdministratorIter.hasNext()){
                    Administrator ad = (Administrator)AdministratorIter.next();
                    if(ad.getNetId().equals(userId)){
                        return Authorization.ADMINISTRATOR;
                    }
                }
            }
            while(InstructorIter.hasNext()){
                if(InstructorIter.hasNext()){
                    Instructor ins = (Instructor)InstructorIter.next();
                    if(ins.getNetId().equals(userId)){
                        return Authorization.INSTRUCTOR;
                    }
                }
            }
        }
        catch (HibernateException he){
            if(tx != null){
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return Authorization.STUDENT;
    }
}
