package core.controller.instructor;

import core.helper.StringResources;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/instructor")
public class InstructorPageController {

    @RequestMapping("view-requests")
    public ModelAndView viewRequests(ModelAndView model) {
        model.setViewName("instructor/home");
        model.addObject("content", "instructor/include/view-requests");
        model.addObject("heading", StringResources.INSTRUCTOR_OPERATIONS.get("viewRequests"));
        return model;
    }

    @RequestMapping("schedule-event")
    public ModelAndView scheduleEvent(ModelAndView model) {
        model.setViewName("instructor/home");
        model.addObject("content", "instructor/include/schedule-event");
        model.addObject("heading", StringResources.INSTRUCTOR_OPERATIONS.get("scheduleEvent"));
        return model;
    }

    @RequestMapping("home")
    public ModelAndView goToHome(ModelAndView model) {
        model.setViewName("instructor/home");
        model.addObject("content", "instructor/include/home-content");
        model.addObject("heading", StringResources.INSTRUCTOR_OPERATIONS.get("home"));
        return model;
    }
}