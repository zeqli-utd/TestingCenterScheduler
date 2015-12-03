package core.controller.instructor;

import core.event.dao.AppointmentDao;
import core.event.dao.ExamDao;
import core.helper.StringResources;
import core.service.TermManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/instructor")
public class InstructorPageController {
    @Autowired
    private ExamDao examDao;
    @Autowired
    private TermManagerService termManager;
    @Autowired
    private AppointmentDao appointmentDao;

    @RequestMapping("/view-requests")
    public ModelAndView viewRequests(ModelAndView model) {
        model.setViewName("instructor-view-requests");
        model.addObject("heading", StringResources.INSTRUCTOR_VIEW_REQUESTS);
        model.addObject("requests", examDao.getAllPending());
        return model;
    }

    @RequestMapping("/schedule-adhoc")
    public ModelAndView scheduleAdhoc(ModelAndView model) {
        model.setViewName("schedule-adhoc");

        return model;
    }

    @RequestMapping("/exam-type")
    public ModelAndView selectExamType (ModelAndView model) {
        model.setViewName("instructor-exam-type");
        return model;
    }

    @RequestMapping("/schedule-event")
    public ModelAndView scheduleRequest(ModelAndView model) {
        model.setViewName("schedule-event");
        model.addObject("terms", termManager.getAllPopulatedTerms());
        return model;
    }

    @RequestMapping("/home")
    public ModelAndView goToHome(ModelAndView model) {
        model.setViewName("instructor-home");
        return model;
    }

    @RequestMapping("/view-appointments")
    public ModelAndView viewAppointments(ModelAndView model) {
        model.setViewName("instructor-view-appointments");
        model.addObject("heading", StringResources.INSTRUCTOR_VIEW_APPOINTMENTS_DETAIL);
        model.addObject("appointments", appointmentDao.findAllAppointment());
        return model;
    }
}