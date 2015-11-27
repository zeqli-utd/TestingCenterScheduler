package core.service;

import core.user.Administrator;
import core.user.Authorization;
import core.user.Instructor;
import core.user.UserType;
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
        Transaction tx = session.beginTransaction();

        List allUserId = session.createQuery("SELECT netId FROM UserType").list();

        boolean result = allUserId.contains(userId);

        session.close();
        return result;

        /*Session session = sessionManager.getInstance().openSession();

        List allUser = session.createQuery("FROM UserType").list();

        //SessionFactory sessionFactory = new Configuration().configure.buildSessionFactory();

        Iterator userIter = allUser.iterator();
        while(userIter.hasNext()){
            UserType user = (UserType)userIter.next();
            if(user.getNetId().equals(userId)){
                session.close();
                return true;
            }
        }
        }

        session.close();
        return false;
        */
    }

    @Override
    public boolean userMatchPassword(String userId, String password) {

        /*Session session = sessionManager.createSessionFactory().openSession();//save object using hibernate
        session.beginTransaction();
        session.save(e);
        session.getTransaction().commit();*/
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = session.beginTransaction();
        List allUser = session.createQuery("FROM UserType").list();

        Iterator userIter = allUser.iterator();
        while(userIter.hasNext()){
            UserType user = (UserType)userIter.next();
            if(user.getNetId().equals(userId)){
                if(user.getPassword().equals(password)){
                    return true;
                }
                session.close();
                return false;
            }
        }
        session.close();
        return false;
    }

    @Override
    public Authorization login(String userId, String password) {
        //Authorization result = Authorization.STUDENT;
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = session.beginTransaction();

        List allAdministrator = session.createQuery("FROM Administrator").list();
        List allInstructor = session.createQuery("FROM Instructor").list();
        List allStudent = session.createQuery("FROM Student").list();

        Iterator AdministratorIter = allAdministrator.iterator();
        Iterator InstructorIter = allInstructor.iterator();
        Iterator StudentIter = allStudent.iterator();

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
        /*while(StudentIter.hasNext()){
            if(StudentIter.hasNext()){
                Student st = (Student)StudentIter.next();
                if(st.getNetId().equals(userId)){
                    result = Authorization.STUDENT;
                }
            }
        }*/
        return Authorization.STUDENT;
    }
}
