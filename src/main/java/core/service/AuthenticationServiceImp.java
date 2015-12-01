package core.service;

import core.user.*;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;

@Repository
public class AuthenticationServiceImp implements AuthenticationService {


    @Override
    public boolean registeredUserId(String userId) {
        Transaction tx = null;
        boolean result = false;
        Session session = SessionManager.getInstance().openSession();
        try {
            tx = session.beginTransaction();
            List allUserId = session.createQuery("SELECT netId FROM core.user.User").list();
            result = allUserId.contains(userId);
            tx.commit();
        } catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
            return false;
        }
        finally {
            session.close();
        }
        return result;
    }

    @Override
    public boolean userMatchPassword(String userId, String password) {
        Transaction tx = null;
        //boolean result = false;
        Session session = SessionManager.getInstance().openSession();
        try{
            tx = session.beginTransaction();

            List allUser = session.createQuery("FROM User").list();
            Iterator userIter = allUser.iterator();

            while (userIter.hasNext()) {
                User user = (User) userIter.next();
                if (user.getNetId().equals(userId)) {
                    if (user.getPassword().equals(password)) {
                        return true;
                    }
                    return false;
                }
            }
            tx.commit();
        } catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
            return false;
        }
        finally {
            session.close();
        }
        return false;
    }

    @Override
    public Authorization login(String userId) {
        Transaction tx = null;
        User user = new User();
        Session session = SessionManager.getInstance().openSession();
        try {
            tx = session.beginTransaction();
            user = session.get(User.class, userId);
            tx.commit();
        } catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
            return Authorization.STUDENT;
        }
        finally {
            session.clear();
        }
        return user.getAuthorization();
    }

}
