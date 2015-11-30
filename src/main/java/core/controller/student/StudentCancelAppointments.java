package core.controller.student;

import core.event.dao.AppointmentDao;
import core.user.SessionProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class StudentCancelAppointments {
    @Autowired
    AppointmentDao appointmentDao;

    @RequestMapping("/student/view-appointments/cancel/{id}")
    public ModelAndView cancelAppointment(@PathVariable("id") String appointmentId,
                                          HttpSession session,
                                          ModelAndView model) {
        SessionProfile profile = (SessionProfile) session.getAttribute("sessionUser");
        appointmentDao.deleteAppointment(appointmentId);
        model.setViewName("redirect:/student/view-appointments");
        model.addObject(appointmentDao.findAllByStudent(profile.getUserId()));
        return model;
    }
}
