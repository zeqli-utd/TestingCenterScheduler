package core.controller.student;

import core.event.dao.AppointmentDao;
import core.service.AppointmentManageService;
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

    @Autowired
    AppointmentManageService appointmentManageService;

    @RequestMapping("/student/view-appointments/cancel/{id}")
    public ModelAndView cancelAppointment(@PathVariable("id") int appointmentId,
                                          HttpSession session,
                                          ModelAndView model) {
        SessionProfile profile = (SessionProfile) session.getAttribute("sessionUser");
        appointmentManageService.cancelAppointment(appointmentId);
        model.setViewName("redirect:/student/view-appointments");
        model.addObject(appointmentDao.findAllByStudent(profile.getUserId()));
        return model;
    }
}
