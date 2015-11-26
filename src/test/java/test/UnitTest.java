package test;

import core.event.*;
import core.service.SessionManager;
import core.user.Administrator;
import core.user.Student;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;


public class UnitTest {

    public static final Logger log = Logger.getLogger(Log4J.class);

    public static void main(String[] args) {
        UnitTest ut = new UnitTest();

        // Basic SQL transaction for Administrator.
        Administrator admin =  new Administrator("admin", "admin", "Admin", "Admin", "Admin@gamil");
        Administrator admin1 = new Administrator("zeqli", "abc","zeq", "li", "gmail");
        Administrator admin2 = new Administrator("yiminz", "abc","yimin", "zhu", "gmail");
        Administrator admin3 = new Administrator("walt", "abc","walt", "wu", "gmail");
        ut.addAdmin(admin1);
        ut.addAdmin(admin2);
        ut.addAdmin(admin3);
        ut.listAdmins();
        admin1.setEmail("zeqing.li@stonybrook.edu");
        ut.updateAdmin(admin1);

        Exam exam1 = new Exam("308","ad hoc", LocalDateTime.of(2015,6,20,13,30),LocalDateTime.of(2015,6,20,15,0), 1.5,50,80, "prof");
        Exam exam2 = new Exam("390","course", LocalDateTime.of(2015,6,20,15,10),LocalDateTime.of(2015,6,20,17,40), 2.5,50,80, "prof");
        ut.addExam(exam1);
        ut.addExam(exam2);
        ut.listExams();

        //Actual utilization part
        Utilization util = new Utilization();


        util.getCenter().setOpen(LocalTime.of(9, 30));
        util.getCenter().setClose(LocalTime.of(17, 0));

        Appointment appointment1 = new Appointment();
        Appointment appointment2 = new Appointment();//
        appointment1.setStartDateTime(LocalDateTime.of(2015, 6, 20, 10, 30));
        appointment1.setEndDateTime(LocalDateTime.of(2015, 6, 20, 12, 30));
        appointment1.setAppointmentID("1");

        appointment2.setStartDateTime(LocalDateTime.of(2015, 6, 20, 13, 30));
        appointment2.setEndDateTime(LocalDateTime.of(2015, 6, 20, 15, 30));
        appointment2.setAppointmentID("2");
        util.getAppointmentDao().insertAppointment(appointment1);
        util.getAppointmentDao().insertAppointment(appointment2);

        util.setNumSeat(60);
        ut.viewActualUtilization(util);
        //

        util.getExamDao().addExam(exam1);
        util.getExamDao().addExam(exam2);
        util.setGap(0.25);
        util.setDay(2);
        ut.viewExpectedUtilization(util);


        // This Section is for check-in Student with Appointment
        Appointment appt1 = new Appointment("cse308examZeqli", "cse_308", "admin", LocalDateTime.of(2015,10,29,1,0),
                LocalDateTime.of(2015,10,29,2,20),"zeqli", "5R11", false);
        Student student = new Student("Robert", "abc", "robert", "li", "l.caecar@gmail.com");
//        student.setAppointments(new ArrayList<>());
//        student.getAppointments().add(appt1);
        ut.addAppointment(appt1);
        ut.addStudent(student);
        // TODO Make a dummy student and make it owner of appointments.
        admin.checkInStudent("cse_308", "zeqli");




        // This Section for view a list of appointment at a specific time
        /**
         * This section will test the view appointments functionality.
         * In database there should be 3 appointments in the Appointment table.
         * respectively cse308examZeqli and cse308examlWal from 1pm to 2:20pm on Oct. 29
         * and cse308examYim from 1pm to 2:20pm on Oct. 30
         * By specifying the time at 1:15pm on Oct. 29. the last line of this section will retrieve
         * only two appointments that occur at that time.
         */
        Appointment walApp = new Appointment("cse308examWal", "cse_308", "wal", LocalDateTime.of(2015,10,29,1,0),
                LocalDateTime.of(2015,10,29,2,20),"wal", "5R13", false);
        Student wal = new Student("wal", "aaa", "wal", "wu", "walt.wu@gmail.com");

        Appointment yimApp = new Appointment("cse308examYim", "cse_308", "yim", LocalDateTime.of(2015,10,30,1,0),
                LocalDateTime.of(2015,10,30,2,20),"yim", "5R15", false);
        Student yim = new Student("yim", "aaa", "yim", "zhu", "yimin.zhu@gmail.com");
        ut.addStudent(wal);
        ut.addStudent(yim);
        ut.addAppointment(walApp);
        ut.addAppointment(yimApp);
        admin.listAllAppointments(LocalDateTime.of(2015, 10, 29, 1, 15));

        // This Section for cancel appointment.

        admin.cancelAppointment(walApp.getAppointmentID());
        admin.listAllAppointments(LocalDateTime.of(2015, 10, 29, 1,15));

        // This Section for edit appointment.
        /**
         *  Let's simulate a case that Administrator have changed a seat in an appointment.
         *  Then the function Adminstrator.updateAppointment(Appointment) is called to update the appointment
         *  in the database.
         */
        yimApp.setSeat("6R1");
        admin.updateAppointment(yimApp);
        admin.listAllAppointments();


        // This Section is for test Edit TestingCenterInfo use case
        TestingCenterInfo info = new TestingCenterInfo();
        info = ut.viewTestingCenterInfo(info);
        ut.saveTestingCenterInfo(info);

        DataCollection data = new DataCollection();
        ut.importDate("./doc/testcases/user.csv");





    }

    // This method will call Administrator.listAllAppointments()
    public void listAdmin(LocalDateTime ldt){

    }

    public void addStudent(Student student){
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(student);
            log.info("---------- addStudent(Student student) ----------");
            log.info("|  -Student Id: " + student.getNetId());
            log.info("|  -Password: " + student.getPassword());
            log.info("|  -First Name: " + student.getFirstName());
            log.info("|  -Last Name" + student.getLastName());
            log.info("|  -Email" + student.getEmail());
            tx.commit();
            session.close();
        }catch (HibernateException he){
            he.printStackTrace();
            if(tx != null){
                tx.rollback();
            }
        }
    }

    // Insert a row into Administrator Table
    public void addAdmin(Administrator admin){
            Session session = SessionManager.getInstance().getOpenSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(admin);
                log.info("---------- addAdmin(Administrator admin)----------");
                log.info("|  -Administrator Id: " + admin.getNetId());
                log.info("|  -Password: " + admin.getPassword());
                log.info("|  -First Name: " + admin.getFirstName());
                log.info("|  -Last Name" + admin.getLastName());
                log.info("|  -Email" + admin.getEmail());
                tx.commit();
                session.close();
            }catch (HibernateException he){
                he.printStackTrace();
                if(tx != null){
                    tx.rollback();
                }
            }
    }

    // SQL VIEW
    public void listAdmins() {
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List e = session.createQuery("FROM Administrator").list();
            Iterator iterator = e.iterator();

            while(iterator.hasNext()) {
                Administrator admin = (Administrator)iterator.next();
                log.info("---------- listAdmins()----------");
                log.info("|  -Administrator Id: " + admin.getNetId());
                log.info("|  -Password: " + admin.getPassword());
                log.info("|  -First Name: " + admin.getFirstName());
                log.info("|  -Last Name" + admin.getLastName());
                log.info("|  -Email" + admin.getEmail());
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
    public void updateAdmin(Administrator admin) {
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Administrator e = (Administrator)session.get(Administrator.class, admin.getNetId());
            e.update(admin);
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
    public void deleteAdmin(String NetId) {
        Session session = SessionManager.getInstance().getOpenSession();
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

    // Insert a row into Administrator Table
    public void addAppointment(Appointment appt){
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.save(appt);
            log.info("---------- addAppointment(Appointment appt) ----------");
            log.info("|  -AppointmentID" + appt.getAppointmentID());
            log.info("|  -EmailId" + appt.getExamId());
            log.info("|  -MadeBy" + appt.getMadeBy() );
            log.info("|  -StartDateTime" + appt.getStartDateTime());
            log.info("|  -EndDateTime" + appt.getEndDateTime());
            log.info("|  -StudentId" + appt.getStudentId());
            log.info("|  -Seat" + appt.getSeat());
            log.info("|  -IsAttended" + appt.isAttend());

            tx.commit();
            session.close();
        }catch (HibernateException he){
            he.printStackTrace();
            if(tx != null){
                tx.rollback();
            }
        }
    }

    public void adminMakeApptForStudentTestCase(){


    }

    public void adminViewApptForASpecificTimeTestCase(){

    }

    public void adminCancelApptTestCase(){

    }

    public void adminEditApptTestCase(){

    }

    public void checkinStudentTestCase(){

    }

    public void addExam(Exam exam){
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(exam);
            log.info("---------- add Exam info----------");
            log.info("|  -Exam Id: " + exam.getExamId());
            log.info("|  -Exam Name: " + exam.getExamName());
            log.info("|  -Type: " + exam.getType());
            log.info("|  -Start Date Time: " + exam.getStartDateTime());
            log.info("|  -End Date Time" + exam.getEndDateTime());
            log.info("|  -Duration: " + exam.getDuration());
            log.info("|  -Number of Students who has appointments: " + exam.getNumStudentAppointment());
            log.info("|  -Number of Students who need to take exam: " + exam.getNumStudentNeed());

            tx.commit();
            session.close();
        }catch (HibernateException he){
            he.printStackTrace();
            if(tx != null){
                tx.rollback();
            }
        }
    }
    public void listExams() {
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List e = session.createQuery("FROM Exam ").list();
            Iterator iterator = e.iterator();

            while(iterator.hasNext()) {
                Exam exam = (Exam)iterator.next();
                log.info("---------- View Exams()----------");
                log.info("|  -Exam Id: " + exam.getExamId());
                log.info("|  -Exam Name: " + exam.getExamName());
                log.info("|  -Type: " + exam.getType());
                log.info("|  -Start Date Time: " + exam.getStartDateTime());
                log.info("|  -End Date Time" + exam.getEndDateTime());
                log.info("|  -Duration: " + exam.getDuration());
                log.info("|  -Number of Students who has appointments: " + exam.getNumStudentAppointment());
                log.info("|  -Number of Students who need to take exam: " + exam.getNumStudentNeed());
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

    public void viewActualUtilization(Utilization util){

            log.info("---------- View Actual Utilization----------");
            log.info("|  -Actual Utilization-: " + util.countUtilzActual()+"%");
        log.info("---------- View Actual Utilization----------");
        //util.setGap(0.25);
    }
    public void viewExpectedUtilization(Utilization util){

        log.info("---------- View Expected Utilization----------");
        log.info("|  -Actual Utilization-: " + util.countUtilzExpection()+"%");
        log.info("---------- View Expected Utilization----------");
        //util.setGap(0.25);
    }
    public TestingCenterInfo viewTestingCenterInfo(TestingCenterInfo info){
        info = TestingCenterInfo.deserialize();
        log.info("---------- viewTestingCenterInformation ----------");
        log.info("|  -Number of Seat: " + info.getNumSeats());
        log.info("|  -Number of Set-Aside-Seats: " + info.getNumSetAsideSeats());
        log.info("|  -Open Hour: " + info.getOpen());
        log.info("|  -Close Hour: " + info.getClose());
        Iterator<LocalDate[]> it = (info.getCloseDateRanges().iterator());
        while(it.hasNext()){
            LocalDate[] item = it.next();
            log.info("|  -Close Date Range: " + item[0]+ " " + item[1]);
        }
        Iterator<LocalDateTime[]> it2 = (info.getReserveRanges().iterator());
        while(it2.hasNext()){
            LocalDateTime[] item = it2.next();
            log.info("|  -Reserve Date Time Range: " + item[0] + " " + item[1]);
        }
        log.info("|  -Gap Time: " + info.getGap());
        log.info("|  -Reminder Interval: " + info.getReminderInterval());
        return info;
    }
    public void saveTestingCenterInfo(TestingCenterInfo info){
        System.out.println(info.getClose());
        info.update(info);
        log.info("---------- updateviewtestingCenterInformation ----------");
        log.info("|  -Number of Seat: " + info.getNumSeats());
        log.info("|  -Number of Set-Aside-Seats: " + info.getNumSetAsideSeats());
        log.info("|  -Open Hour: " + info.getOpen());
        log.info("|  -Close Hour: " + info.getClose());
        Iterator<LocalDate[]> it = (info.getCloseDateRanges().iterator());
        while(it.hasNext()){
            LocalDate[] item = it.next();
            log.info("|  -Close Date Range: " + item[0]+ " " + item[1]);
        }
        Iterator<LocalDateTime[]> it2 = (info.getReserveRanges().iterator());
        while(it2.hasNext()){
            LocalDateTime[] item = it2.next();
            log.info("|  -Reserve Date Time Range: " + item[0] + " " + item[1]);
        }
        log.info("|  -Gap Time: " + info.getGap());
        log.info("|  -Reminder Interval: " + info.getReminderInterval());
        info = TestingCenterInfo.deserialize();
        log.info("---------- saveTestingCenterInformation ----------");
        log.info("|  -Number of Seat: " + info.getNumSeats());
        log.info("|  -Number of Set-Aside-Seats: " + info.getNumSetAsideSeats());
        log.info("|  -Open Hour: " + info.getOpen());
        log.info("|  -Close Hour: " + info.getClose());
        Iterator<LocalDate[]> it3 = (info.getCloseDateRanges().iterator());
        while(it.hasNext()){
            LocalDate[] item = it3.next();
            log.info("|  -Close Date Range: " + item[0]+ " " + item[1]);
        }
        Iterator<LocalDateTime[]> it4 = (info.getReserveRanges().iterator());
        while(it2.hasNext()){
            LocalDateTime[] item = it4.next();
            log.info("|  -Reserve Date Time Range: " + item[0] + " " + item[1]);
        }
        log.info("|  -Gap Time: " + info.getGap());
        log.info("|  -Reminder Interval: " + info.getReminderInterval());
    }

    public void importDate(String path){
        DataCollection data = new DataCollection();
        int listType = data.readFile(path);
        List<String[]> list = data.getList();
        if(listType == 1){
            //add student
            for(int i = 0; i < list.size(); i++){
                String[] items = list.get(i);
                Student student = new Student();
                student.setFirstName(items[0]);
                student.setLastName(items[1]);
                student.setNetId(items[2]);
                student.setEmail(items[3]);
                addStudent(student);
            }
        }
        else if(listType == 2){
            //add class
            for(int i = 0; i < list.size(); i++){
                String[] items = list.get(i);
                Course course = new Course();
                course.setCourseId(items[0]);
                course.setSubject(items[1]);
                course.setCatalog(Integer.parseInt(items[2]));
                course.setSession(Integer.parseInt(items[3]));
                course.setInstructorID(items[4]);
            }
        }
        else if(listType == 3){
            //add rooster, added
        }
    }

}
