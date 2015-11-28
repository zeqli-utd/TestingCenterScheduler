package core.user.dao;

import core.service.SessionManager;
import core.user.Authorization;
import core.user.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;


public class UserDaoImp implements UserDao {


    @Override
    public List<User> getAllUserAccounts() {
        return null;
    }

    @Override
    public List<User> getUserAccountsByName(String firstName, String lastName) {
        return null;
    }

    @Override
    public User getUserAccountById(int Id) {
        return null;
    }

    @Override
    public boolean updateInfo(User user) {
        return false;
    }

    @Override
    public boolean deleteUserAccount(String netid) {
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            User user = (User)session.get(User.class, netid);
            session.delete(user);
            tx.commit();
        } catch (HibernateException var8) {
            if(tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return true;
    }

    @Override
    public boolean addUserAccount(User user) {
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(user);
            tx.commit();
            session.close();
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
}
