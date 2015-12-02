package core.service;

import core.event.*;
import core.event.dao.AdhocExamDao;
import core.event.dao.AppointmentDao;
import core.event.dao.ExamDao;
import core.event.dao.TestingCenterTimeSlotsDao;
import core.helper.StringResources;
import core.user.SessionProfile;
import core.user.User;
import core.user.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handle exam approval.
 */
@Service
public class ExamManageService {

    @Autowired
    TestingCenterInfoRetrieval tcr;

    @Autowired
    AppointmentDao appointmentDao;

    @Autowired
    private TestingCenterTimeSlotsDao tctsDao;

    @Autowired
    private EmailService emailService;

    @Autowired
    ExamDao examDao;

    @Autowired
    AdhocExamDao adhocExamDao;

    @Autowired
    UserDao userDao;



    public ExamManageService() {

    }

    // Schedule an Exam
    public boolean addExam(Exam newExam, SessionProfile profile) {
        ExamType examType = newExam.getExamType();
        boolean result = true;
        if (examType.equals(ExamType.REGULAR)) {
            // If Regular Exam
            result = examDao.addExam(newExam);

            // If
        } else {
            result = adhocExamDao.addAdhocExam((AdhocExam)newExam);
        }
        if(result){
            User user = userDao.getUserById(profile.getUserId());
            String emailAddress = user.getEmail();
            try {
                emailService.sendEmail(StringResources.EMAIL_HOST, StringResources.EMAIL_PORT,
                        StringResources.EMAIL_LOGIN, StringResources.EMAIL_PASSWORD,
                        emailAddress, "Exam Request Notification", "You successfully send a exam request");
            } catch (MessagingException e) {
                // Do nothing
            }
        }
        return result;
    }



    /**
     * Approve Exam.
     *
     * @param examId
     * @return
     */
    public boolean approveExam(String examId) {
        // Upon Approved, Generate Time Slots
        Exam examToApproved = examDao.findByExamId(examId);

        List<TestingCenterTimeSlots> timeSlots = generateTimeSlots(examToApproved);

        tctsDao.insertTimeSlots(timeSlots);

        examToApproved.setStatusType(ExamStatusType.APROVED);
        if (examDao.updateExam(examToApproved)) {
            return true;
        } else {
            return false;
        }
    }

    // Calculate How many time
    public List<TestingCenterTimeSlots> generateTimeSlots(Exam exam) {
        TestingCenterInfo tci = tcr.findByTerm(tcr.getCurrentTerm().getTermId());
        int gap = tci.getGap();
        int examDuration = exam.getDuration();
        int openMinutes = (int) ChronoUnit.MINUTES.between(tci.getOpen(), tci.getClose());

        // Ensure time chunk devides 30
        int timeChuck = (examDuration + gap) % 30 == 0 ? examDuration : 30 * ((examDuration + gap) / 30 + 1);


        LocalTime endTime = exam.getEndDateTime().toLocalTime();
        LocalTime beginTime = exam.getStartDateTime().toLocalTime();
        LocalTime openTime = tci.getOpen();
        LocalTime closeTime = tci.getClose();
        LocalDate beginDate = exam.getStartDateTime().toLocalDate();
        LocalDate endDate = exam.getEndDateTime().toLocalDate();

        beginTime = adjustTime(beginTime);// Exam Begin Time
        endTime = adjustTime(endTime);  // Exam End Time


        // Calculate Duration According to different day.
        int startDayDuration = (int) ChronoUnit.MINUTES.between(beginTime, closeTime);
        int dayLast = (int) ChronoUnit.DAYS.between(beginDate, endDate) - 1;
        int endDayDuration = (int) ChronoUnit.MINUTES.between(openTime, endTime);

        int startDayChucks = startDayDuration / timeChuck;
        int endDayChucks = endDayDuration / timeChuck;
        int regularDayChuncks = dayLast >= 0 ? (dayLast * openMinutes) / timeChuck : 0;
        int dailyChuncks = openMinutes / timeChuck;


        LocalDate dateCursor = beginDate;
        LocalTime timeCursor = beginTime;
        List<TestingCenterTimeSlots> timeSlotses = new ArrayList<>();
        if (startDayChucks > 0) {
            for (int i = 0; i < startDayChucks; i++) {
                LocalDateTime slotsBegin = dateCursor.atTime(timeCursor);
                LocalDateTime slotsEnd = dateCursor.atTime(timeCursor.plusMinutes(examDuration));

                TestingCenterTimeSlots t = new TestingCenterTimeSlots(exam.getExamId(), slotsBegin, slotsEnd,
                        tci.getNumSeats(), tci.getNumSetAsideSeats());
                timeSlotses.add(t);

                timeCursor = timeCursor.plusMinutes(timeChuck);
            }
        }
        if (regularDayChuncks > 0) {

            for (int i = 0; i < dayLast; i++) {
                dateCursor = dateCursor.plusDays(1);
                timeCursor = adjustTime(openTime);

                for (int j = 0; j < dailyChuncks; j++) {
                    LocalDateTime slotsBegin = dateCursor.atTime(timeCursor);
                    LocalDateTime slotsEnd = dateCursor.atTime(timeCursor.plusMinutes(examDuration));

                    TestingCenterTimeSlots t = new TestingCenterTimeSlots(exam.getExamId(), slotsBegin, slotsEnd,
                            tci.getNumSeats(), tci.getNumSetAsideSeats());
                    timeSlotses.add(t);

                    timeCursor = timeCursor.plusMinutes(timeChuck);
                }
            }

        }
        if (endDayChucks > 0) {
            dateCursor = dateCursor.plusDays(1);
            timeCursor = adjustTime(openTime);

            for (int i = 0; i < endDayChucks; i++) {
                LocalDateTime slotsBegin = dateCursor.atTime(timeCursor);
                LocalDateTime slotsEnd = dateCursor.atTime(timeCursor.plusMinutes(examDuration));

                TestingCenterTimeSlots t = new TestingCenterTimeSlots(exam.getExamId(), slotsBegin, slotsEnd,
                        tci.getNumSeats(), tci.getNumSetAsideSeats());
                timeSlotses.add(t);

                timeCursor = timeCursor.plusMinutes(timeChuck);
            }
        }
        return timeSlotses;
    }

    // Adjust time to on hour or half hour
    public LocalTime adjustTime(LocalTime time) {
        if (!(time.getMinute() == 0 || time.getMinute() == 30)) {
            if (time.getMinute() > 30) {
                time = time.plusHours(1);
                time = time.withMinute(0);
            } else if (time.getMinute() < 30) {
                time = time.withMinute(30);
            } else {
            }
        }
        return time;
    }
}
