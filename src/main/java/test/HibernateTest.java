package test;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import core.user.Administrator;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import java.util.Iterator;
import java.util.List;

public class HibernateTest {
    private static SessionFactory factory;
    private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;

    public HibernateTest() {
    }

    public static SessionFactory createSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure("/Test/hibernate.cfg.xml");
        serviceRegistry = (new ServiceRegistryBuilder()).applySettings(configuration.getProperties()).buildServiceRegistry();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return sessionFactory;
    }

    public static void main(String[] args) {
        try {
            factory = createSessionFactory();
        } catch (Throwable var2) {
            System.err.println("Failed to create sessionFactory object." + var2);
            throw new ExceptionInInitializerError(var2);
        }

        HibernateTest HT = new HibernateTest();
        HT.addAdmin("zeqli", "abc", "Zeqing", "Li","gmail");
        HT.addAdmin("ww", "abc", "Walt", "Wu","gmail");
        HT.addAdmin("yz", "abc", "Yimin", "Zhu","gmail");
        HT.listAdmins();
        HT.updateEmployee("zeqli", "xxxx");
        HT.deleteAdministrator("ww");
        HT.listAdmins();
    }

    // SQL ADD
    public void addAdmin(String netId, String pwd, String fname, String lname, String email) {
        Session session = factory.openSession();
        Transaction tx = null;
        Object employeeID = null;

        try {
            tx = session.beginTransaction();
            Administrator e = new Administrator(netId, pwd, fname, lname, email);
            session.save(e);
            tx.commit();
        } catch (HibernateException var12) {
            if(tx != null) {
                tx.rollback();
            }

            var12.printStackTrace();
        } finally {
            session.close();
        }

    }

    // SQL VIEW
    public void listAdmins() {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List e = session.createQuery("FROM Administrator").list();
            Iterator iterator = e.iterator();

            while(iterator.hasNext()) {
                Administrator admin = (Administrator)iterator.next();
                System.out.println(" NetID: " + admin.getNetId());
                System.out.print("First Name: " + admin.getFirstName());
                System.out.print(" Last Name: " + admin.getLastName());
                System.out.println(" Password: " + admin.getPassword());
            }

            tx.commit();
        } catch (HibernateException var9) {
            if(tx != null) {
                tx.rollback();
            }

            var9.printStackTrace();
        } finally {
            session.close();
        }
    }

    // SQL UPDATE
    public void updateEmployee(String NetId, String lastName) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Administrator e = (Administrator)session.get(Administrator.class, NetId);
            e.setLastName(lastName);
            session.update(e);
            tx.commit();
        } catch (HibernateException var9) {
            if(tx != null) {
                tx.rollback();
            }

            var9.printStackTrace();
        } finally {
            session.close();
        }

    }

    // SQL DELETE
    public void deleteAdministrator(String NetId) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Administrator e = (Administrator)session.get(Administrator.class, NetId);
            session.delete(e);
            tx.commit();
        } catch (HibernateException var8) {
            if(tx != null) {
                tx.rollback();
            }

            var8.printStackTrace();
        } finally {
            session.close();
        }

    }
}
