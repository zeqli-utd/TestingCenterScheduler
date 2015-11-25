package core.controller.student;

import core.event.dao.AppointmentDao;
import core.user.dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/student")
public class StudentAppointmentController {
    @Autowired
    StudentDao studentDao;
    @Autowired
    AppointmentDao appointmentDao;

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

    @RequestMapping("make-appointment/new/{examId}")
    public String makeAppointment(@PathVariable("examId") String examId,
                                  HttpSession session) {
        //get logged in user from session
        String userId = (String) session.getAttribute("sessionUserId");

        return "";
    }
}