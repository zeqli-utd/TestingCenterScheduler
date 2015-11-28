package core.controller.student;

import core.event.dao.AppointmentDao;
import core.event.dao.ExamDao;
import core.helper.StringResources;
import core.user.SessionProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/student")
@SessionAttributes("sessionUser")
public class StudentPageController {
    @Autowired
    private ExamDao examDao;
    @Autowired
    private AppointmentDao appointmentDao;

    @RequestMapping("view-appointments")
    public ModelAndView viewAppointments(@ModelAttribute("sessionUser") SessionProfile profile,
                                         ModelAndView model) {
        model.setViewName("student/view-appointments");
        model.addObject("appointments",
                appointmentDao.findAllByStudent(profile.getUserId()));
        return model;
    }

    @RequestMapping("make-appointment")
    public ModelAndView makeAppointment(ModelAndView model) {
        model.setViewName("student/make-appointment");
        model.addObject("exams", examDao.getAllApproved());
        model.addObject("pageHeading", StringResources.STUDENT_MAKE_APPOINTMENT);
        return model;
    }
}