package core.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class SessionManager {
    private static SessionManager instance;
    private static SessionFactory sessionFactory;
    private static StandardServiceRegistryBuilder serviceRegistryBuilder;
    private static ServiceRegistry serviceRegistry;

    public SessionManager() {
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
            sessionFactory = createSessionFactory();
        }
        return instance;
    }

    private static SessionFactory createSessionFactory() {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(core.user.Administrator.class)
                .addAnnotatedClass(core.user.UserType.class)
                .addAnnotatedClass(core.user.Student.class)
                .addAnnotatedClass(core.user.Instructor.class)
                .addAnnotatedClass(core.user.User.class)
                .addAnnotatedClass(core.event.AdhocExam.class)
                .addAnnotatedClass(core.event.Appointment.class)
                .addAnnotatedClass(core.event.CloseDateRangeTuple.class)
                .addAnnotatedClass(core.event.Course.class)
                .addAnnotatedClass(core.event.ETSTestTimeRangeTuple.class)
                .addAnnotatedClass(core.event.Exam.class)
                .addAnnotatedClass(core.event.Reservation.class)
                .addAnnotatedClass(core.event.Roster.class)
                .addAnnotatedClass(core.event.StudentEntry.class)
                .addAnnotatedClass(core.event.Term.class)
                .addAnnotatedClass(core.event.TestingCenterInfo.class)
                .addAnnotatedClass(core.event.TestingCenterTimeSlots.class)
                .addAnnotatedClass(core.event.Seat.class)
                .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                .setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
                .setProperty("hibernate.connection.url", "jdbc:mysql://sbu-cse-308.capjijo2fevl.us-east-1.rds.amazonaws.com:3306/testing_center")
                .setProperty("hibernate.connection.username", "thedueteam")
                .setProperty("hibernate.connection.password", "thedueteam")
//                .setProperty("hibernate.hbm2ddl.auto", "create-drop") // For init
                .setProperty("hibernate.hbm2ddl.auto", "update")    // For update
                .setProperty("hibernate.show_sql", "true");
        serviceRegistryBuilder = new StandardServiceRegistryBuilder().applySettings(
                configuration.getProperties());
        serviceRegistry = serviceRegistryBuilder.build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return sessionFactory;
    }

//      Old one, this use configuration file
//    private static SessionFactory createSessionFactory() {
//        Configuration configuration = new Configuration();
//        configuration.configure();
//        serviceRegistryBuilder = new StandardServiceRegistryBuilder().applySettings(
//                configuration.getProperties());
//        serviceRegistry = serviceRegistryBuilder.build();
//        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
//        return sessionFactory;
//    }


    /**
     * @return
     */
    public Session openSession() {
        return sessionFactory.openSession();
    }


    // USE CASE - SEE Administrator Class
//    public Appointment getAppointment(String apptID){
    // Open a session
//        Session session = sessionManager.getInstance().openSession();
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
