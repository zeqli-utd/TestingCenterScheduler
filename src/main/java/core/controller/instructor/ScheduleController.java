package core.controller.instructor;

import core.event.Exam;
import core.event.dao.ExamDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/schedule-event")
public class ScheduleController {
    @Autowired
    ExamDao examDao;

    @RequestMapping("/submit")
    public ModelAndView SubmitScheduleRequestForm(@ModelAttribute("newExam")Exam exam,
                                                  ModelAndView model) {
        model.setViewName("/instructor/home");
        if (examDao.addExam(exam)) {
            model.addObject("errorMessage", "Could not add new exam.");
        }
        model.addObject("content", "/instructor/include/schedule-event");
        return model;
    }
}
