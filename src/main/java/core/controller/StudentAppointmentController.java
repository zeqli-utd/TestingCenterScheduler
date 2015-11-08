package core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StudentAppointmentController {

    @RequestMapping(value = "find-exam/by-instructor",
            method = RequestMethod.POST, params = "by-instructor")
    public ModelAndView findExamByInstructor() {
        ModelAndView model = new ModelAndView("student-make-appointment");

        return model;
    }

    @RequestMapping(value = "find-exam/by-course",
            method = RequestMethod.POST, params = "by-course")
    public ModelAndView findExamByCourse() {
        ModelAndView model = new ModelAndView("student-make-appointment");
        return model;
    }
}
