package core.service;

import core.CancelAppointmentException;
import core.MakeAppointmentException;
import core.event.*;
import core.event.dao.AppointmentDao;
import core.event.dao.ExamDao;
import core.event.dao.RosterDao;
import core.event.dao.TestingCenterTimeSlotsDao;
import core.helper.StringResources;
import core.user.User;
import core.user.dao.UserDao;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class AppointmentManageService {
    @Autowired
    AppointmentDao appointmentDao;

    @Autowired
    TestingCenterTimeSlotsDao tctsDao;

    @Autowired
    EmailService emailService;

    @Autowired
    UserDao userDao;

    @Autowired
    ExamDao examDao;

    @Autowired
    RosterDao rosterDao;

    /**
     * This method handle make appointment.
     *
     * @param newAppointment
     * @param timeSlots
     * @return
     */
    public boolean makeAppointment(Appointment newAppointment, TestingCenterTimeSlots timeSlots) throws MakeAppointmentException{

        String examId = newAppointment.getExamId();
        Exam exam = examDao.findByExamId(examId);
        if(exam == null){
            throw  new MakeAppointmentException("Make Appointment Fail: Exam Not Found");
        }

        // A. Test Eligibility
        if (!testEligibility(exam, newAppointment)){
            throw  new MakeAppointmentException("Make Appointment Fail: No Eligible to make an appointment");
        }


        // B. Student don't have exist appointment for the same exam
        if(testDuplicateAppointment(newAppointment)){
            throw new MakeAppointmentException("Make Appointment Fail: Duplicate Appointment");
        }

        // C. Find Appointment Time Conflict
        if(testAppointmentOverlap(newAppointment)){
            throw new MakeAppointmentException("Make Appointment Fail: Appointment Time Conflict");
        }

        // D. Appointment Must In Exam Time
        // No needed

        // Test if MadeBy Admin

        boolean isAdmin = userDao.isAdmin(newAppointment.getMadeBy());


        // E. A Non-set-aside seat is available
        appointmentDao.insertAppointment(newAppointment);
        boolean sucAssign;
        if(isAdmin){
            if(timeSlots.getNumSetAsideSeat() != 0){    // Ensure there are enough set aside seat.
                sucAssign = timeSlots.assignSetAsideSeat(newAppointment);
            }else{
                sucAssign = false;
            }

        }else {
            sucAssign = timeSlots.assignSeat(newAppointment);
        }

        if(!sucAssign){
            // Roll Back Assignment
            appointmentDao.deleteAppointment(newAppointment.getAppointmentID());
            throw  new MakeAppointmentException("Make Appointment Fail: No more Seat");
        }

        // No we can make appointment
        boolean isSuccess = appointmentDao.makeAppointment(newAppointment, timeSlots);

        // Fail
        if(!isSuccess){
            // Release Seat
            TestingCenterTimeSlots tcts = tctsDao.findTimeSlotById(newAppointment.getSlotId());
            tcts.releaseSeat(newAppointment);
            tctsDao.updateTimeSlot(tcts);

            // and Roll Back
            appointmentDao.deleteAppointment(newAppointment.getAppointmentID());

            throw  new MakeAppointmentException("Make Appointment Fail: Cannot save to database");
        }
        // Success
        else{
            User user = userDao.getUserById(newAppointment.getStudentId());
            String emailAddress = user.getEmail();
            try {
                emailService.sendEmail(StringResources.EMAIL_HOST, StringResources.EMAIL_PORT,
                        StringResources.EMAIL_LOGIN, StringResources.EMAIL_PASSWORD,
                        emailAddress, "Appointment Notification", "You successfully make an appointment!\n" +
                                "Your Appointment Id is " + newAppointment.getAppointmentID());
            } catch (MessagingException e) {
                // Do nothing
            }
            return true;
        }
    }

    /**
     * CancelAppointment
     *
     * @param appId
     * @return
     */
    public boolean cancelAppointment(int appId) throws CancelAppointmentException{
        // 1. Check Before 24 hours
        Appointment appointment = appointmentDao.findAppointmentById(appId);
        if (appointment == null){
            new CancelAppointmentException("Can not find appointment");
        }
        if(ChronoUnit.HOURS.between (LocalDateTime.now(), appointment.getStartDateTime())<24){
            new CancelAppointmentException("Cannot Cancel Appointment: Exam starts less than 24 hours");
        }

        //need to release seat when delete appointment
        Appointment appt = appointmentDao.findAppointmentById(appId);
        TestingCenterTimeSlots tcts = tctsDao.findTimeSlotById(appt.getSlotId());
        tcts.releaseSeat(appt);
        tctsDao.updateTimeSlot(tcts);

        if (appointmentDao.deleteAppointment(appId)) {
            return true;
        } else {
            return false;
        }

    }


    /**
     * Check in Student By Administrator
     * @param apptId
     * @return
     */
    public boolean checkinStudent(int apptId){
        Appointment appt = appointmentDao.findAppointmentById(apptId);
        appt.setIsAttend(true);
        if(appointmentDao.updateAppointment(appt)){
            return true;
        }
        return false;
    }


    /**
     * Test A. if an appointment is eligible to be schedule.
     *
     * @param appt
     * @return True if eligible to make appointment
     */
    public boolean testEligibility(Exam exam ,Appointment appt){
        // 1. Check Student in Ad Hoc List
        if (exam.getExamType().equals(ExamType.AD_HOC)) {
            boolean isInList = false;
            List<StudentEntry> studentEntries = ((AdhocExam) exam).getStudentList();
            for (StudentEntry se : studentEntries) {
                if (se.getNetId().equals(appt.getStudentId())) {
                    isInList = true;
                }
            }
            if (!isInList){
                return false;   // Not in Adhoc list
            }
        } else {
            // 2. Check Student Enroll in Course
            Roster roster = rosterDao.findRoster(exam.getCourseId(), appt.getStudentId(), exam.getTerm());
            if(roster == null){
                return false;     // Not in Course
            }
        }
        return true;
    }

    /**
     * Test B. Student don't have exist appointment for the same exam
     * @param appt
     * @return True if found duplicate, False otherwise
     */
    public boolean testDuplicateAppointment(Appointment appt){
        return appointmentDao.checkDuplicateExam(appt.getExamId(), appt.getStudentId());
    }


    /**
     * Test C. Find Appointment Time Conflict
     * @param appt
     * @return
     */
    public boolean testAppointmentOverlap(Appointment appt){
        return appointmentDao.checkOverlap(appt);
    }

    // No need to test D. entirely with exam start and end time.


    public boolean checkLegalAppointment(Appointment a) {

        String studentIdCheck = a.getStudentId();
        String examIdCheck = a.getExamId();
        boolean res = true;

        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Exam exam = (Exam) session.get(Exam.class, examIdCheck);

            // 1. Check Student in Ad Hoc List
            if (exam.getExamType().equals(ExamType.AD_HOC)) {
                List<StudentEntry> studentEntries = ((AdhocExam) exam).getStudentList();
                for (StudentEntry se : studentEntries) {
                    if (se.getNetId().equals(studentIdCheck)) {
                        break;
                    }
                }
            } else {
                // 2. Check Student Enroll in Course
                Roster roster = (Roster) session.get(Roster.class,
                        new Roster(exam.getCourseId(), studentIdCheck, a.getTerm()));
                if (roster == null) {
                    return false;
                }
            }

            //the appt time is during exam time period
            // d. Check appointment is entirely between the start date-time and end date-time of exam.
            if ((exam.getStartDateTime().isBefore(a.getStartDateTime()))
                    && (exam.getEndDateTime().isAfter(a.getEndDateTime()))) {
                //map to object
                String hql = "FROM Appointment b WHERE b.studentId = :stuId";
                String stuId = studentIdCheck;
                Query query = session.createQuery(hql);
                query.setParameter("stuId", "%" + stuId + "%");
                List<Appointment> list = query.list();
                Iterator it = list.iterator();

                TestingCenterInfo testingCenterInfo = (TestingCenterInfo) session.
                        get(TestingCenterInfo.class, exam.getTerm());


                ArrayList<Appointment> possibleLists = new ArrayList<Appointment>();
                while (it.hasNext()) {
                    Appointment appt = new Appointment();
                    appt = (Appointment) it.next();
                    // b. Does not have an existing appointment for the same exam.
                    if (!appt.getExamId().equals(examIdCheck)) {
                        //c. Student does not have an appointment for a different exam
                        // in an overlapping timeslot and gap
                        if (((a.getStartDateTime().minusMinutes(testingCenterInfo.getGap())).isAfter(appt.getEndDateTime())) ||
                                (a.getEndDateTime().isBefore((appt.getStartDateTime().minusMinutes(testingCenterInfo.getGap()))))) {
                            //continue
                            possibleLists.add(appt);
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }

                Appointment apptIter;
                int i = 0;
                while (i < possibleLists.size()) {
                    apptIter = possibleLists.get(i);
                    // E. Non-set-aside seat is available
                    String timeSlotId = Integer.toString(a.getStartDateTime().getDayOfYear()) +
                            Integer.toString(a.getEndDateTime().getHour()) + Integer.toString(a.
                            getStartDateTime().getMinute());
                    TestingCenterTimeSlots testingCenterTimeSlots = (TestingCenterTimeSlots)
                            session.get(TestingCenterTimeSlots.class, timeSlotId);
                    if (testingCenterTimeSlots.checkSeatAvailable()) {
                        testingCenterTimeSlots.assignSeat(apptIter);
                        return true;
                    }
                    i++;
                }
                return false;
            } else {
                return false;
            }
        } catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return true;
    }
}
