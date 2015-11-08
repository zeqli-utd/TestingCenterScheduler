package core.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class SessionManager {
    private static SessionManager instance;
    private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;

    public SessionManager() {}

    public static SessionManager getInstance() {
        if (instance==null){
            instance = new SessionManager();
            sessionFactory = createSessionFactory();
        }
        return instance;
    }

    public static SessionFactory createSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure();
        serviceRegistry = new ServiceRegistryBuilder().applySettings(
                configuration.getProperties()). buildServiceRegistry();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return sessionFactory;
    }


    public Session getOpenSession(){
        return sessionFactory.openSession();
    }


    // USE CASE - SEE Administrator Class
//    public Appointment getAppointment(String apptID){
    // Open a session
//        Session session = sessionManager.getInstance().getOpenSession();
//        Transaction tx = null;
    //
//        Appointment appt = null;

//        try {
//            tx = session.beginTransaction();
//            appt = (Appointment)session.get(Appointment.class, apptID);
//            ** Other transaction
//            session.delete(Object)
//            session.add(Object)
//            List e = (List) session.createQuery("FROM xxxx").list();
//
//            ** Logger
//
//            tx.commit();
//            session.close();
//        }catch (HibernateException he){
//            he.printStackTrace();
//            if(tx != null){
//                tx.rollback();
//            }
//        }
//        return appt;
//    }

}
