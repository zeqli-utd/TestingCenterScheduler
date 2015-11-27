package core.controller.student;

import core.event.dao.ExamDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/student")
public class StudentPageController {
    @Autowired
    private ExamDao examDao;

    private ModelAndView model = new ModelAndView();

    @RequestMapping("view-appointments")
    public ModelAndView viewAppointments() {
        model.clear();
        model.setViewName("student/view-appointments");
        return model;
    }

    @RequestMapping("make-appointment")
    public ModelAndView makeAppointment() {
        model.clear();
        model.setViewName("student/home");
        model.addObject("content", "include/make-appointment");
        model.addObject("exams", examDao.getAllExams());
        return model;
    }
}