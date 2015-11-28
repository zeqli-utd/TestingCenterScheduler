package core.user.dao;

import core.service.SessionManager;
import core.user.Authorization;
import core.user.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;


public class UserDaoImp implements UserDao {


    @Override
    public List<User> getAllUsers() {
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        List<User> userList = new ArrayList<>();
        try {
            tx = session.beginTransaction();
            userList = session.createQuery("FROM core.user.User").list();

            tx.commit();
        } catch (HibernateException e) {
            if(tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return userList;
    }

    @Override
    public List<User> getUserByName(String firstName, String lastName) {
        return null;
    }

    @Override
    public User getUserById(int netid) {
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        User user = null;
        try {
            tx = session.beginTransaction();
            user = (User)session.get(User.class, netid);
            tx.commit();
        } catch (HibernateException he) {
            if(tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return user;
    }

    @Override
    public boolean updateInfo(User user) {
        return false;
    }

    @Override
    public boolean deleteUser(String netid) {
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
    public boolean addUser(User user) {
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
