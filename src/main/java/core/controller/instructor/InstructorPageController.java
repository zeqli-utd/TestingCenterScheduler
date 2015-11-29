package core.controller.instructor;

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

    @RequestMapping("/view-requests")
    public ModelAndView viewRequests(ModelAndView model) {
        model.setViewName("instructor/view-requests");
        model.addObject("heading", StringResources.INSTRUCTOR_VIEW_REQUESTS);
        model.addObject("requests", examDao.getAllPending());
        return model;
    }

    @RequestMapping("/schedule-event")
    public ModelAndView scheduleEvent(ModelAndView model) {
        model.setViewName("instructor/schedule-event");
        model.addObject("heading", StringResources.INSTRUCTOR_SCHEDULE);
        model.addObject("terms", termManager.getAllPopulatedTerms());
        return model;
    }

    @RequestMapping("/home")
    public ModelAndView goToHome(ModelAndView model) {
        model.setViewName("instructor/home");
        model.addObject("heading", "Home");
        return model;
    }
}