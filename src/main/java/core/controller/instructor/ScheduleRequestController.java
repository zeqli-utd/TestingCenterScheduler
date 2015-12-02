package core.controller.instructor;

import core.event.Exam;
import core.event.ExamType;
import core.event.dao.ExamDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/instructor/schedule-event")
public class ScheduleRequestController {
    @Autowired
    ExamDao examDao;

    @RequestMapping(value = "/submit")
    public ModelAndView SubmitScheduleRequestForm(@ModelAttribute Exam exam,
                                                  ModelAndView model) {
        exam.setExamType(ExamType.REGULAR);
        model.setViewName("redirect:/instructor/view-requests");
        if (!examDao.addExam(exam)) {
            model.addObject("errorMessage", "Could not add new exam.");
        }else {
            model.addObject("errorMessage", "Request submitted.");
        }
        return model;
    }
}
