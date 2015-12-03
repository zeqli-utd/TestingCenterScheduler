package core.controller.instructor;

import core.event.dao.AppointmentDao;
import core.helper.StringResources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/instructor/view-appointments")
public class ViewAppointmentsDetailController {
    @Autowired
    private AppointmentDao appointmentDao;

    @RequestMapping("view-appointments/{examId}")
    public ModelAndView viewRequests(@PathVariable("examId") String examId,
                                     ModelAndView model) {
        model.setViewName("instructor-home");
        model.addObject("heading", StringResources.INSTRUCTOR_VIEW_APPOINTMENTS_DETAIL);
        model.addObject("appointments", appointmentDao.findAllAppointmentsByExamId(examId));
        return model;
    }
}