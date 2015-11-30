package test;

import core.event.*;
import core.event.dao.AdhocExamDaoImp;
import core.event.dao.AppointmentDaoImp;
import core.event.dao.ExamDaoImp;
import core.event.dao.TestingCenterTimeSlotsDaoImp;
import core.helper.StringResources;
import core.service.EmailService;
import core.service.TermManagerService;
import core.service.TestingCenterInfoRetrieval;
import core.user.Student;
import core.user.dao.InstructorDaoImp;
import core.user.dao.StudentDaoImp;
import core.user.dao.UserDaoImp;
import core.user.Authorization;
import core.user.User;
import core.user.dao.UserDao;
import core.user.dao.UserDaoImp;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.List;

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


    @BeforeClass
    public static void InitializeTestingCenterInfo(){
        User admin = new User("admin", "123", "admin", "admin", "admin@example", Authorization.ADMINISTRATOR);
        User student = new User("student", "123", "student", "student", "student@example", Authorization.STUDENT);

        UserDao dao = new UserDaoImp();
        dao.addUser(admin);
        dao.addUser(student);
    }


    @BeforeClass
    public static void SetTermInformation(){

        Term term1 = new Term(1158, LocalDate.of(2015, 8, 22), LocalDate.of(2015, 12, 17) );
        Term term2 = new Term(1161,LocalDate.of(2015, 12, 25), LocalDate.of(2016, 1, 23));
        Term term3 = new Term(1164,LocalDate.of(2016, 1, 24), LocalDate.of(2016, 5, 20));

        TermManagerService tm = new TermManagerService();
        tm.insertTerm(term1);
        tm.insertTerm(term2);
        tm.insertTerm(term3);



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
    @Test
    public void TestUtilization(){
        //Exam exam1 = new Exam("308","ad hoc", LocalDateTime.of(2015,6,20,10,30),LocalDateTime.of(2015,6,20,15,0), 4.5,50,80, "prof");
        //Exam exam2 = new Exam("390","course", LocalDateTime.of(2015,6,20,15,10),LocalDateTime.of(2015,6,20,18,40), 2.5,50,80, "prof");
        Utilization util = new Utilization();

        TestingCenterInfo testingCenterInfo = new TestingCenterInfo();
        testingCenterInfo.setTerm(1158);
        testingCenterInfo.setOpen(LocalTime.of(9, 0));
        testingCenterInfo.setClose(LocalTime.of(17, 0));
        TestingCenterInfoRetrieval testingCenterInfoRetrieval = new TestingCenterInfoRetrieval();
        testingCenterInfoRetrieval.insertTestingCenterInfo(testingCenterInfo);

        TestingCenterTimeSlotsDaoImp TimeSlotsDaoImp = new TestingCenterTimeSlotsDaoImp();
        TestingCenterTimeSlots slot = new TestingCenterTimeSlots("rId", LocalDateTime.of(2015,10,2,10,10), LocalDateTime.of(2015,10,2,11,20),
        64, 2);
        TimeSlotsDaoImp.insertTimeSlot(slot);

        Exam ex = new Exam("rId", "rName",64,1158,LocalDateTime.of(2015,10,2,10,10),LocalDateTime.of(2015,10,2,11,20), "robert","rTest",60);

        AdhocExam ad = new AdhocExam("aId", "aName",64,1158,LocalDateTime.of(2015,10,2,11,40),LocalDateTime.of(2015,10,2,12,40), "aobert","aTest",60);
        //StudentEntry se = new StudentEntry("zeqli", "zeqing", "li");
        //ad.getStudentList().add(se);

        // Persist
        adhocExamDaoImp = new AdhocExamDaoImp();
        adhocExamDaoImp.addAdhocExam(ad);

        examDaoImp = new ExamDaoImp();
        examDaoImp.insertExam(ex);

        util.countUtilzActual(LocalDateTime.of(2015, 10, 2, 5, 10));

        Appointment appointment1 = new Appointment();
        Appointment appointment2 = new Appointment();//
        appointment1.setStartDateTime(LocalDateTime.of(2015,10,2,5,10));
        appointment1.setEndDateTime(LocalDateTime.of(2015, 10, 2, 5, 40));
        appointment1.setAppointmentID(1);

        appointment2.setStartDateTime(LocalDateTime.of(2015, 10, 2, 5, 10));
        appointment2.setEndDateTime(LocalDateTime.of(2015, 10, 2, 5, 40));
        appointment2.setAppointmentID(2);

        util.getAppointmentDao().insertAppointment(appointment1);
        util.getAppointmentDao().insertAppointment(appointment2);

        util.setNumSeat(64);
        System.out.println("Actual Utilization: " + util.countUtilzActual(LocalDateTime.of(2015,10,2,5,10)));
        //
        //util.setGap(0.25);
        //util.setDay(2);
        //util.getExamDao().addExam(exam1);
        //util.getExamDao().addExam(exam2);
        //System.out.println(util.countUtilzExpection());
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
//    public void TestMakeAppointment(){
//        TestingCenterTimeSlots initSlot = new TestingCenterTimeSlots(
//                "examId",
//                LocalDateTime.of(2015,10,2,5,10),
//                LocalDateTime.of(2015,10,2,6,20),
//                64,
//                5
//        );
//
//        TestingCenterTimeSlotsDaoImp dao = new TestingCenterTimeSlotsDaoImp();
//        dao.insertTimeSlot(initSlot);
//
//
//        Appointment appointment = new Appointment();
//        TestingCenterTimeSlots slot
//                = dao.findTimeSlotById(initSlot.getTimeSlotId());
//        appointment.setExamId(slot.getExamId());
//        appointment.setStartDateTime(slot.getBegin());
//        appointment.setEndDateTime(slot.getEnd());
//        appointment.setStudentId("Zeqli");
//        appointment.setExamName("Exam Name");
//        appointment.setTerm(1158);
//
//        AppointmentDaoImp appointmentDaoImp = new AppointmentDaoImp();
//        appointmentDaoImp.insertAppointment(appointment);
//
//        Assert.assertEquals(
//                dao.findTimeSlotById(initSlot.getTimeSlotId()).getSeatArrangement().get(0),
//                appointmentDaoImp.findAppointmentById(appointment.getAppointmentID()).getAppointmentID());
//        ;
//        ;
//
//    }
//
//        TestingCenterTimeSlotsDaoImp dao = new TestingCenterTimeSlotsDaoImp();
//        dao.insertTimeSlot(initSlot);
//
//
//        Appointment appointment = new Appointment();
//        TestingCenterTimeSlots slot
//                = dao.findTimeSlotById(initSlot.getTimeSlotId());
//        appointment.setExamId(slot.getExamId());
//        appointment.setStartDateTime(slot.getBegin());
//        appointment.setEndDateTime(slot.getEnd());
//        appointment.setStudentId("Zeqli");
//        appointment.setExamName("Exam Name");
//        appointment.setTerm(1158);
//
//        AppointmentDaoImp appointmentDaoImp = new AppointmentDaoImp();
//        appointmentDaoImp.insertAppointment(appointment);
//
//        Assert.assertEquals(
//                dao.findTimeSlotById(initSlot.getTimeSlotId()).getSeatArrangement().get(0),
//                appointmentDaoImp.findAppointmentById(appointment.getAppointmentID()).getAppointmentID());
//        ;
//        ;
//
//    }

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
//   @Test
//    public void TestEmailReminder(){
//       boolean isSuccess = true;
//        try {
//            EmailService.sendEmail(StringResources.EMAIL_HOST, StringResources.EMAIL_PORT,
//                    StringResources.EMAIL_LOGIN, StringResources.EMAIL_PASSWORD,
//                    StringResources.EMAIL_LOGIN,"test","test");
//        } catch (MessagingException e) {
//           isSuccess = false;
//
//        }
//        Assert.assertEquals(true, isSuccess);
//
//    }

    

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
//    @Test
//    public void TestAdhocExam(){
//        Exam ex = new Exam("rId", "rName",64,1158,LocalDateTime.of(2015,10,2,5,10),LocalDateTime.of(2015,10,2,6,20), "robert","rTest",60);
//
//        AdhocExam ad = new AdhocExam("aId", "aName",64,1158,LocalDateTime.of(2015,10,2,1,10),LocalDateTime.of(2015,10,2,5,30), "aobert","aTest",60);
//        StudentEntry se = new StudentEntry("zeqli", "zeqing", "li");
//        ad.getStudentList().add(se);
//
//        // Persist
//        adhocExamDaoImp = new AdhocExamDaoImp();
//        adhocExamDaoImp.addAdhocExam(ad);
//
//        examDaoImp = new ExamDaoImp();
//        examDaoImp.insertExam(ex);
//
//        // Check whether we can retrieve adhoc exam from exam table.
//        List<Exam> examList = examDaoImp.getAllPending();
//        for (Exam e: examList){
//            if (e.getExamType().equals(ExamType.AD_HOC)){
//                List<StudentEntry> studentEntries= ((AdhocExam)e).getStudentList();
//                Assert.assertEquals(se, studentEntries.get(0));
//            }
//        }
//    }

//        // Persist
//        adhocExamDaoImp = new AdhocExamDaoImp();
//        adhocExamDaoImp.addAdhocExam(ad);
//        examDaoImp = new ExamDaoImp();
//        examDaoImp.insertExam(ex);
//
//        // Check whether we can retrieve adhoc exam from exam table.
//        List<Exam> examList = examDaoImp.getAllPending();
//        for (Exam e: examList){
//            if (e.getExamType().equals(ExamType.AD_HOC)){
//                List<StudentEntry> studentEntries= ((AdhocExam)e).getStudentList();
//                Assert.assertEquals(se, studentEntries.get(0));
//            }
//        }
//    }

    @Test
    public void TestCloseTime(){


    }






}
