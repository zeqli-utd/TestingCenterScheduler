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

//    @RequestMapping("make-appointment/new/{examId}")
//    public String makeAppointment(@PathVariable("examId") String examId, HttpSession session) {
//        //get logged in user from session
//        String userId = (String) session.getAttribute("sessionUserId");
//
//        return "";
//    }
    @RequestMapping(value = "/makeAppointment", method = RequestMethod.POST)
    public ModelAndView makeAppointment(){
        ModelAndView model =  new ModelAndView("student/make-appointment");

    }

}