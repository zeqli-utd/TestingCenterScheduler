package core.controller.student;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StudentAppointmentController {

    @RequestMapping(value = "find-exam/by-instructor",
            method = RequestMethod.POST, params = "by-instructor")
    public ModelAndView findExamByInstructor() {
        return new ModelAndView("student/include/make-appointment");

    }

    @RequestMapping(value = "find-exam/by-course",
            method = RequestMethod.POST, params = "by-course")
    public ModelAndView findExamByCourse() {
        return new ModelAndView("student/include/make-appointment");
    }
}
