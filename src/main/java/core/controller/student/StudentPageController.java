package core.controller.student;

import core.event.dao.AppointmentDao;
import core.event.dao.ExamDao;
import core.helper.StringResources;
import core.user.SessionProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/student")
public class StudentPageController {
    @Autowired
    private ExamDao examDao;
    @Autowired
    private AppointmentDao appointmentDao;

    @RequestMapping("/home")
    public ModelAndView goToHome (ModelAndView model) {
        model.setViewName("student-home");
        return model;
    }

    @RequestMapping("/view-appointments")
    public ModelAndView viewAppointments(HttpSession session,
                                         ModelAndView model) {
        SessionProfile profile = (SessionProfile) session.getAttribute("sessionUser");
        model.setViewName("student-view-appointments");
        model.addObject("appointments",
                appointmentDao.findAllByStudent(profile.getUserId()));
        model.addObject("exams");
        return model;
    }

    @RequestMapping("/make-appointment")
    public ModelAndView makeAppointment(ModelAndView model) {
        model.setViewName("student-make-appointment");
        model.addObject("exams", examDao.getAllApproved());
        model.addObject("pageHeading", StringResources.STUDENT_MAKE_APPOINTMENT);
        return model;
    }
}