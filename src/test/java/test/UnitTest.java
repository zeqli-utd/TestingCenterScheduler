package test;

import core.event.*;
import core.event.dao.*;
import core.service.TestingCenterInfoRetrieval;
import core.user.Student;
import core.user.dao.InstructorDaoImp;
import core.user.dao.StudentDaoImp;
import core.user.dao.UserDaoImp;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import java.util.List;

import java.time.LocalDateTime;

//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
//@ContextConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
public class UnitTest  {

    private static SessionFactory sessionFactory;

//    @Autowired
//    private CourseDaoImp courseDaoImp;
//    @Autowired
    private ExamDaoImp examDaoImp;
//    @Autowired
    private AdhocExamDaoImp adhocExamDaoImp;
//    @Autowired
//    private RosterDaoImp rosterDaoImp;
//    @Autowired
//    private TestingCenterInfoRetrieval testingCenterInfoRetrieval;
//    @Autowired
//    private AppointmentDaoImp appointmentDaoImp;
//    @Autowired
//    private TestingCenterTimeSlotsDaoImp testingCenterTimeSlotsDaoImp;
//    @Autowired
//    private InstructorDaoImp instructorDaoImp;
//    @Autowired
//    private StudentDaoImp studentDaoImp;
//    @Autowired
//    private UserDaoImp userDaoImp;



    public static SessionFactory getSessionFactory() {
        SessionFactory sessionFactory;
        StandardServiceRegistryBuilder serviceRegistryBuilder;
        ServiceRegistry serviceRegistry;

        Configuration configuration = new Configuration()
                .addAnnotatedClass(core.user.Administrator.class)
                .addAnnotatedClass(core.user.UserType.class)
                .addAnnotatedClass(core.user.Student.class)
                .addAnnotatedClass(core.user.Instructor.class)
                .addAnnotatedClass(core.user.Instructor.class)
                .addAnnotatedClass(core.user.Instructor.class)
                .addAnnotatedClass(core.event.Exam.class)
                .addAnnotatedClass(core.event.Course.class)
                .addAnnotatedClass(core.event.Utilization.class)
                .addAnnotatedClass(core.event.Appointment.class)
                .addAnnotatedClass(core.event.Reservation.class)
                .addAnnotatedClass(core.event.Course.class)
                .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                .setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
                .setProperty("hibernate.connection.url", "jdbc:mysql://Localhost:3306/test")
                .setProperty("hibernate.connection.username", "root")
                .setProperty("hibernate.connection.password", "thedueteam")
                .setProperty("hibernate.hbm2ddl.auto", "create-drop")
                .setProperty("hibernate.show_sql", "true");
        serviceRegistryBuilder = new StandardServiceRegistryBuilder().applySettings(
                configuration.getProperties());
        serviceRegistry = serviceRegistryBuilder.build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return sessionFactory;
    }

    @BeforeClass
    public static void ConnectDB() {
        sessionFactory = getSessionFactory();
    }


    /*---------------------Function for Admin--------------------*/


    /**
     * Edit testing center information 4
     */
//    @Test
    public void TestEditTCI(){}


    /**
     * Import data (2 pts for handling superfluous appointments) 5
     */
//    @Test
    public void TestImportDate(){}


    /**
     * Display utilization during a specified date range (3 pts each for actual utilization and 6
     * expected utilization; each of these includes 1pt for taking gap time into account)
     */
//    @Test
    public void TestUtilization(){

    }

    /**
     * Approve or deny an exam scheduling request for the current or a future term 6
     * (3 pts for schedulability test, 1 pt for current utiliz., 1 pt for utiliz. with this request)
     */
//    @Test
    public void TestApplyOrDeniedExam(){}



    /**
     * Make an appointment on behalf of a student (2 pts for handling set-aside seats) 3
     */
//    @Test
    public void TestMakeAppointmentOnBehalf(){}



    /**
     * View appointments 3
     */
//    @Test
    public void TestViewAppointment(){}

    /**
     * Cancel or edit any appointment 3
     */
//    @Test
    public void TestCancelOrEditAppointment(){}


    /**
     * Check-in a student for an appointment (2 pt for basic function, 1 pt for ease-of-use) 3
     */
//    @Test
    public void TestCheckInStudent(){}


    /**
     * Generate reports (2 pts each) 8
     */
//    @Test
    public void TestGenerateReport(){}

    /*---------------------Function for Instructor--------------------*/


    /**
     * Exam scheduling request for the current or a future term 9
     * 3 pts for basic functionality (enter and store the request), 2 pts for ad hoc exams,
     * 2 pts for schedulability test, 1 pt for current utilization, 1 pt for utilization with
     * current and all pending requests
     */
//    @Test
    public void TestExamSchedule(){}

    /**
     * Cancel a pending exam scheduling request 3
     */
//    @Test
    public void TestCancelPendingExam(){}

    /**
     * See a list of his or her exam scheduling requests in the current and future terms 3
     */
//    @Test
    public void ViewExams(){}

    /**
     * See appointment and attendance details for a specified exam 3
     */
//    @Test
    public void ViewExamDetails(){}

    /*---------------------Function for Instructor--------------------*/
    /**
     * Make an appointment to take an exam. 16
     2 pts for basic functionality, 2 pts for showing available timeslots, 2 pts each for
     items a through d, 3 pts for item e (availability test, including 1 pt for set-aside seats
     and 1 pt for effect of gap time), 1 pt for avoiding placing students taking same exam
     in adjacent seats
     */

//    @Test
    public void TestMakeAppointment(){}

    /**
     *  Cancel an appointment 3
     */
//    @Test
    public void CancelAppointment(){}

    /**
     * See a list of his or her appointments in a specified past, current, or future term 3
     */
//    @Test
    public void TestAppointments(){}


    /**
     * Appointment reminders sent by email. 4
     *
     */
//    @Test
    public void TestEmailReminder(){}

    /*---------------------Other Requirement-------------------*/
    /**
     * Authentication 3
     */
//    @Test
    public void TestAuthentication(){}

    /**
     * Network security (TA checks for https in web-based systems, examines 3
            networking code in non-web systems)
     */
//    @Test
    public void TestNetworkSecurity(){}

    /**
     * Not vulnerable to URL replay (must be demo'd for web-based system) or other 2
            common vulnerabilities (TA might suggest attacks to try during demo)
     */
//    @Test
    public void TestVulerability(){}

    /**
     * Concurrency (2 pts for two instructors concurrently trying to schedule an exam when 4
     * testing center is near capacity, and the second one to submit should fail; 2 pts for
     * two students concurrently trying to get the last available seat in a timeslot)
     */
//    @Test
    public void TestConcurrency(){
        //TODO 1. check if it is a valid exam
        // 2. check when add to exam table, is their exist an exam overlap with it.
    }

    /**
     * Test Read Adhoc Exam from Exam Table
     */
    @Test
    public void TestAdhocExam(){
        Exam ex = new Exam("rId", "rName",64,1158,LocalDateTime.of(2015,10,2,5,10),LocalDateTime.of(2015,10,2,6,20), "robert","rTest",60);
        AdhocExam ad = new AdhocExam("aId", "aName",64,1158,LocalDateTime.of(2015,10,2,1,10),LocalDateTime.of(2015,10,2,5,30), "aobert","aTest",60);
        StudentEntry se = new StudentEntry("zeqli", "zeqing", "li");
        ad.getStudentList().add(se);

        // Persist
        adhocExamDaoImp = new AdhocExamDaoImp();
        adhocExamDaoImp.addAdhocExam(ad);
        examDaoImp = new ExamDaoImp();
        examDaoImp.insertExam(ex);

        // Check whether we can retrieve adhoc exam from exam table.
        List<Exam> examList = examDaoImp.getAllPending();
        for (Exam e: examList){
            if (e.getExamType().equals(ExamType.AD_HOC)){
                List<StudentEntry> studentEntries= ((AdhocExam)e).getStudentList();
                Assert.assertEquals(se, studentEntries.get(0));
            }
        }
    }

    @Test
    public void TestCloseTime(){


    }






}
