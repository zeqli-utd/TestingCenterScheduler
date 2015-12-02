package test;

import core.event.*;
import core.event.dao.*;
import core.service.*;
import core.user.Authorization;
import core.user.User;
import core.user.dao.InstructorDao;
import core.user.dao.StudentDao;
import core.user.dao.UserDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
public class UnitTest {


    @Autowired
    private CourseDao courseDao;
    @Autowired
    private ExamDao examDaoImp;
    @Autowired
    private AdhocExamDao adhocExamDao;

    @Autowired
    private RosterDao rosterDao;
    @Autowired
    private TestingCenterInfoRetrieval testingCenterInfoRetrieval;
    @Autowired
    private AppointmentDao appointmentDao;
    @Autowired
    private TestingCenterTimeSlotsDao timeSlotsDao;
    @Autowired
    private InstructorDao instructorDao;
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ExamDao examDao;

    @Autowired
    private ExamManageService ems;

    @Autowired
    private AuthenticationService authen;

    @Autowired
    AppointmentManageService ams;

    @Autowired
    private DataCollection dc;

    private String path = "./src/main/resources/1158/";

    @Before
    public void initEntities() {
        initTerm();

        initTestingCenterInfo();

        initAccountDatabase();

        initExams();


    }


    public void initTerm(){
        // 1. Initialize Term
        Term winter2015 = new Term(1151, LocalDate.of(2015, 1, 6), LocalDate.of(2015, 1,24) );
        Term spring2015 = new Term(1154,LocalDate.of(2015, 1, 26), LocalDate.of(2015, 5, 22));
        Term summer2015 = new Term(1156,LocalDate.of(2015, 5, 25), LocalDate.of(2015, 8, 15));
        Term fall2015 = new Term(1158,LocalDate.of(2015, 8, 24), LocalDate.of(2015, 12, 17));

        Term winter2016 = new Term(1161,LocalDate.of(2016, 1,18), LocalDate.of(2016, 1,24) );
        Term spring2016 = new Term(1164,LocalDate.of(2016, 1, 25), LocalDate.of(2016, 5,20));
        Term summer2016 = new Term(1166,LocalDate.of(2016, 5,30), LocalDate.of(2016, 8,20));
        Term fall2016 = new Term(1168,LocalDate.of(2016, 8, 29), LocalDate.of(2016, 12, 22));

        Term winter2017 = new Term(1171,LocalDate.of(2017, 1, 2), LocalDate.of(2017, 1, 21) );
        Term spring2017 = new Term(1174,LocalDate.of(2017, 1, 23), LocalDate.of(2017, 5,19));
        Term summer2017 = new Term(1176,LocalDate.of(2017, 5, 29), LocalDate.of(2017, 8,19));
        Term fall2017 = new Term(1178,LocalDate.of(2017, 1, 24), LocalDate.of(2017, 5, 20));

        TermManagerService tms = new TermManagerService();
        tms.insertTerm(winter2015);
        tms.insertTerm(spring2015);
        tms.insertTerm(summer2015);
        tms.insertTerm(fall2015);

        tms.insertTerm(winter2016);
        tms.insertTerm(spring2016);
        tms.insertTerm(summer2016);
        tms.insertTerm(fall2016);

        tms.insertTerm(winter2017);
        tms.insertTerm(spring2017);
        tms.insertTerm(summer2017);
        tms.insertTerm(fall2017);

    }

    public void initAccountDatabase() {
        User admin = new User("admin", "123", "admin", "admin", "admin@example", Authorization.ADMINISTRATOR);
        User student = new User("student", "123", "student", "student", "student@example", Authorization.STUDENT);
        User student1 = new User("student1", "123", "student", "student", "student@example", Authorization.STUDENT);
        User ins = new User("instructor", "123", "instructor", "instructor", "instructor@example", Authorization.INSTRUCTOR);

        userDao.addUser(admin);
        userDao.addUser(student);
        userDao.addUser(student1);
        userDao.addUser(ins);
    }


    public void initTestingCenterInfo(){
        int[] terms = {1151, 1154, 1156, 1158,1161, 1164, 1166, 1158,1171, 1174, 1176, 1178};
        for (int term: terms){
            TestingCenterInfo t = new TestingCenterInfo(term);
            testingCenterInfoRetrieval.insertTestingCenterInfo(t);
        }
    }

    public void initExams(){
        Exam ex = new Exam(
                "CSE308-01-1158_ex1",
                "CSE308-01-1158_ex1",
                64,
                1158,
                LocalDateTime.of(2015, 10, 2, 5, 10),
                LocalDateTime.of(2015, 10, 2, 6, 20),
                "scott",
                "CSE308-01-1158",
                60);
        AdhocExam ad = new AdhocExam(
                "1158_01_ex1",
                "Math Placement",
                64,
                1158,
                LocalDateTime.of(2015, 10, 2, 1, 10),
                LocalDateTime.of(2015, 10, 2, 5, 30),
                "scott",
                "1158_01",
                60);
        StudentEntry se1 = new StudentEntry("zeqli", "zeqing", "li");
        StudentEntry se2 = new StudentEntry("yisw", "yishuo", "wang");
        ad.getStudentList().add(se1);
        ad.getStudentList().add(se2);

        examDao.addExam(ex);
        adhocExamDao.addAdhocExam(ad);
        // Persist

    }


    /*---------------------Function for Admin--------------------*/


    /**
     * Edit testing center information 4
     */
//    @Test
    public void TestEditTCI() {
    }


    /**
     * Import data (2 pts for handling superfluous appointments) 5
     */
//    @Test
    public void TestImportClass() {
        String classPath = path + "class.csv";
        Assert.assertEquals(true,dc.readFile(classPath, 1158));
    }

//    @Test
    public void TestImportInstructor() {
        String instructorPath = path + "instructor.csv";
        Assert.assertEquals(true,dc.readFile(instructorPath, 1158));
    }

//    @Test
    public void TestImportRoster() {
        String rosterPath = path + "roster.csv";
        Assert.assertEquals(true,dc.readFile(rosterPath, 1158));
    }

//    @Test
    public void TestImportUser() {
        String userPath = path + "user.csv";
        Assert.assertEquals(true,dc.readFile(userPath, 1158));
    }


    /**
     * Display utilization during a specified date range (3 pts each for actual utilization and 6
     * expected utilization; each of these includes 1pt for taking gap time into account)
     */

    //@Test
//    public void TestUtilization() {
//        //Exam exam1 = new Exam("308","ad hoc", LocalDateTime.of(2015,6,20,10,30),LocalDateTime.of(2015,6,20,15,0), 4.5,50,80, "prof");
//        //Exam exam2 = new Exam("390","course", LocalDateTime.of(2015,6,20,15,10),LocalDateTime.of(2015,6,20,18,40), 2.5,50,80, "prof");
//        Utilization util = new Utilization();
//
//        TestingCenterInfo testingCenterInfo = new TestingCenterInfo();
//        testingCenterInfo.setTerm(1158);
//        testingCenterInfo.setOpen(LocalTime.of(9, 0));
//        testingCenterInfo.setClose(LocalTime.of(17, 0));
//        TestingCenterInfoRetrieval testingCenterInfoRetrieval = new TestingCenterInfoRetrieval();
//        testingCenterInfoRetrieval.insertTestingCenterInfo(testingCenterInfo);
//
//        TestingCenterTimeSlotsDaoImp TimeSlotsDaoImp = new TestingCenterTimeSlotsDaoImp();
//        TestingCenterTimeSlots slot = new TestingCenterTimeSlots("rId", LocalDateTime.of(2015, 10, 2, 10, 10), LocalDateTime.of(2015, 10, 2, 11, 20),
//                64, 2);
//        TimeSlotsDaoImp.insertTimeSlot(slot);
//    }

//    @Test
    public void TestUtilization() {

        Utilization util = new Utilization();

        Exam ex = new Exam("rId", "rName",64,1158,LocalDateTime.of(2015,10,2,10,10),LocalDateTime.of(2015,10,2,11,20), "robert","rTest",60);
        AdhocExam ad = new AdhocExam("aId", "aName",64,1158,LocalDateTime.of(2015,10,2,11,40),LocalDateTime.of(2015,10,2,12,40), "aobert","aTest",60);
        //StudentEntry se = new StudentEntry("zeqli", "zeqing", "li");
        //ad.getStudentList().add(se);

        // Persist
        adhocExamDao.addAdhocExam(ad);
        examDao.insertExam(ex);

        util.countUtilzActual(LocalDateTime.of(2015, 10, 2, 5, 10));

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
    public void TestApplyOrDeniedExam() {
        ems.approveExam("1158_01_ex1");
    }


    /**
     * Make an appointment on behalf of a student (2 pts for handling set-aside seats) 3
     */
//    @Test
    public void TestMakeAppointmentOnBehalf() {
    }


    /**
     * View appointments 3
     */
//    @Test
    public void TestViewAppointment() {
    }

    /**
     * Cancel or edit any appointment 3
     */
//    @Test
    public void TestCancelOrEditAppointment() {
    }


    /**
     * Check-in a student for an appointment (2 pt for basic function, 1 pt for ease-of-use) 3
     */
//    @Test
    public void TestCheckInStudent() {
    }


    /**
     * Generate reports (2 pts each) 8
     */
//    @Test
    public void TestGenerateReport() {
    }

    /*---------------------Function for Instructor--------------------*/


    /**
     * Exam scheduling request for the current or a future term 9
     * 3 pts for basic functionality (enter and store the request), 2 pts for ad hoc exams,
     * 2 pts for schedulability test, 1 pt for current utilization, 1 pt for utilization with
     * current and all pending requests
     */
//    @Test
    public void TestExamSchedule() {
    }

    /**
     * Cancel a pending exam scheduling request 3
     */
//    @Test
    public void TestCancelPendingExam() {
    }

    /**
     * See a list of his or her exam scheduling requests in the current and future terms 3
     */
//    @Test
    public void ViewExams() {
    }

    /**
     * See appointment and attendance details for a specified exam 3
     */
    //    @Test
    public void ViewExamDetails() {
    }

    /*---------------------Function for Instructor--------------------*/

    /**
     * Make an appointment to take an exam. 16
     * 2 pts for basic functionality, 2 pts for showing available timeslots, 2 pts each for
     * items a through d, 3 pts for item e (availability test, including 1 pt for set-aside seats
     * and 1 pt for effect of gap time), 1 pt for avoiding placing students taking same exam
     * in adjacent seats
     */

   // @Test
    public void TestMakeAppointment(){
//    @Test
//    public void TestMakeAppointment() {
//        // 1. Insert slot
//        TestingCenterTimeSlots initSlot = new TestingCenterTimeSlots(
//                "examId",
//                LocalDateTime.of(2015, 10, 2, 5, 10),
//                LocalDateTime.of(2015, 10, 2, 6, 20),
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
//        appointmentDao.insertAppointment(appointment);
//
//        Assert.assertEquals(
//                dao.findTimeSlotById(initSlot.getTimeSlotId()).getSeatArrangement().get(0),
//                appointmentDao(appointment.getAppointmentID()).getAppointmentID());
//        ;
//
//
//    }

//        // 2. Initialize appointment
//        TestingCenterTimeSlots slot = dao.findTimeSlotById(initSlot.getTimeSlotId());
//
//
//        // 4. Persist Appointments, should be Automatically Assign One Another Seats
//        AppointmentDaoImp appointmentDaoImp = new AppointmentDaoImp();
//        appointmentDaoImp.insertAppointment(ap1);
//
//
//        // 5. Test Successfully Add One Student into a seat
//        String left = dao.findTimeSlotById(initSlot.getTimeSlotId()).getSeatArrangement().get(0);
//        String right = appointmentDaoImp.findAppointmentById(ap1.getAppointmentID()).getAppointmentID();
//        Assert.assertEquals(left, right);
    }

//    @Test
//    public void TestAppointmentAdjacentSeat(Appointment ap1, Appointment ap2){
//
//
//
//        // 4. Persist Appointments, should be Automatically Assign One Another Seats
//        AppointmentDaoImp appointmentDaoImp = new AppointmentDaoImp();
//        appointmentDaoImp.insertAppointment(ap1);
//        appointmentDaoImp.insertAppointment(ap2);
//
//
//        // 5. Test Successfully Add One Student into a seat
////        String left = testingCenterTimeSlotsDao.findTimeSlotById(initSlot.getTimeSlotId()).getSeatArrangement().get(0);
////        String right = appointmentDaoImp.findAppointmentById(ap1.getAppointmentID()).getSeat();
////        String test = appointmentDaoImp.findAppointmentById(ap2.getAppointmentID()).getAppointmentID();
////        Assert.assertEquals(left, right);
//    }

    /**
     * Cancel an appointment 3
     */
//    @Test
    public void CancelAppointment() {
    }

    /**
     * See a list of his or her appointments in a specified past, current, or future term 3
     */
//    @Test
    public void TestAppointments() {
    }


    /**
     * Appointment reminders sent by email. 4
     */

//   @Test
//    public void TestEmailReminder() {
//        boolean isSuccess = true;
//        try {
//            EmailService.sendEmail(StringResources.EMAIL_HOST, StringResources.EMAIL_PORT,
//                    StringResources.EMAIL_LOGIN, StringResources.EMAIL_PASSWORD,
//                    StringResources.EMAIL_LOGIN, "test", "test");
//        } catch (MessagingException e) {
//            isSuccess = false;
//        }
//        Assert.assertEquals(true, isSuccess);
//    }
//    @Test
//    public void TestEmailReminder() {
//        try {
//            EmailService.sendEmail(StringResources.EMAIL_HOST, StringResources.EMAIL_PORT,
//                    StringResources.EMAIL_LOGIN, StringResources.EMAIL_PASSWORD,
//                    StringResources.EMAIL_LOGIN,"test","test");
//        } catch (MessagingException e) {
//
//        }
    //}


    

    /*---------------------Other Requirement-------------------*/

    /**
     * Authentication 3
     */
//    @Test
    public void TestAuthentication() {
        Assert.assertEquals(true,authen.userMatchPassword("admin", "123"));
    }

//    @Test
    public void TestGetAuthen() {
        Assert.assertEquals(Authorization.ADMINISTRATOR,authen.login("admin"));

    }

    /**
     * Network security (TA checks for https in web-based systems, examines 3
     * networking code in non-web systems)
     */
//    @Test
    public void TestNetworkSecurity() {
    }

    /**
     * Not vulnerable to URL replay (must be demo'd for web-based system) or other 2
     * common vulnerabilities (TA might suggest attacks to try during demo)
     */
//    @Test
    public void TestVulerability() {
    }

    /**
     * Concurrency (2 pts for two instructors concurrently trying to schedule an exam when 4
     * testing center is near capacity, and the second one to submit should fail; 2 pts for
     * two students concurrently trying to get the last available seat in a timeslot)
     */
//    @Test
    public void TestConcurrency() {
        //TODO 1. check if it is a valid exam
        // 2. check when add to exam table, is their exist an exam overlap with it.
    }

    /**
     * Test Read Adhoc Exam from Exam Table
     */
//    @Test
//    public void TestAdhocExam() {
//        Exam ex = new Exam("rId", "rName", 64, 1158, LocalDateTime.of(2015, 10, 2, 5, 10), LocalDateTime.of(2015, 10, 2, 6, 20), "robert", "rTest", 60);
//        AdhocExam ad = new AdhocExam("aId", "aName", 64, 1158, LocalDateTime.of(2015, 10, 2, 1, 10), LocalDateTime.of(2015, 10, 2, 5, 30), "aobert", "aTest", 60);
//        StudentEntry se = new StudentEntry("zeqli", "zeqing", "li");
//        ad.getStudentList().add(se);
//
//        examDao.addExam(ex);
//        adhocExamDao.addAdhocExam(ad);
//        // Persist
//
//        // Check whether we can retrieve adhoc exam from exam table.
//        List<Exam> examList = examDao.getAllPending();
//        for (Exam e : examList) {
//            if (e.getExamType().equals(ExamType.AD_HOC)) {
//                List<StudentEntry> studentEntries = ((AdhocExam) e).getStudentList();
//                Assert.assertEquals(se, studentEntries.get(0));
//            }
//        }
//    }

//    @Test
    public void TestCloseTime() {


    }

//    @Test
    public void TestAdjustTime(){
        LocalTime time = LocalTime.of(5,40);
        // Adjust time to on hour or half hour
            if(time.getMinute()!=0 || time.getMinute()!=30){
                if(time.getMinute()>30){
                    time = time.plusHours(1);
                    time = time.withMinute(0);
                }else if(time.getMinute() < 30){
                    time = time.withMinute(30);
                }else{}
            }

        LocalTime expected = LocalTime.of(6,0);
        Assert.assertEquals(true, expected.equals(time) );

    }

//    @Test
    public void TestSlotsGenerate(){
        Exam exam = examDao.findByExamId("1158_01_ex1");
        List<TestingCenterTimeSlots> timeSlotses = ems.generateTimeSlots(exam);
        timeSlotsDao.insertTimeSlots(timeSlotses);

        TestingCenterTimeSlots slot = timeSlotsDao.findTimeSlotById("2751030");

        Appointment ap1 = new Appointment();
        ams.makeAppointment(ap1, slot);



    }

//    @Test
    public void TestArrangement(){
        TestingCenterTimeSlots slot = timeSlotsDao.findTimeSlotById("27590");
        Appointment ap1 = new Appointment(
                slot.getExamId(),
                slot.getTimeSlotId(),
                "zeqli",
                "robert",
                slot.getBegin(),
                slot.getEnd()
        );
        ams.makeAppointment(ap1, slot);
    }

//    @Test
    // Success
    public void TestApproveExam(){
        ems.approveExam("CSE308-01-1158_ex1");
    }

    @Test // Success
    public void TestReleaseSeat(){
        appointmentDao.deleteAppointment(1442);

    }

}
