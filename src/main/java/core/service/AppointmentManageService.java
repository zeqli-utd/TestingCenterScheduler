package core.service;

import core.event.Appointment;
import core.event.TestingCenterTimeSlots;
import core.event.dao.AppointmentDao;
import core.event.dao.TestingCenterTimeSlotsDao;
import core.helper.StringResources;
import core.user.User;
import core.user.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

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

    /**
     * This method handle make appointment.
     *
     * @param newAppointment
     * @param timeSlots
     * @return
     */
    public boolean makeAppointment(Appointment newAppointment, TestingCenterTimeSlots timeSlots) {
        boolean result = appointmentDao.makeAppointment(newAppointment, timeSlots);
        if (result) {

            User user = userDao.getUserById(newAppointment.getStudentId());
            String emailAddress = user.getEmail();
            try {
                emailService.sendEmail(StringResources.EMAIL_HOST, StringResources.EMAIL_PORT,
                        StringResources.EMAIL_LOGIN, StringResources.EMAIL_PASSWORD,
                        emailAddress, "Appointment Notification", "You successfully make an appointment");
            } catch (MessagingException e) {
                // Do nothing
            }
            return true;
        }
        return false;
    }

    /**
     * CancelAppointment
     *
     * @param appId
     * @return
     */
    public boolean cancelAppointment(int appId) {
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
}
