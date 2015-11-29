package core.controller.instructor;

import core.event.Exam;
import core.event.dao.ExamDao;
import core.helper.StringResources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/instructor/schedule-event")
public class ScheduleController {
    @Autowired
    ExamDao examDao;

    //TODO If it is an Adhoc Exam, it should be passed as Ad Hoc Exam Type along with a List of Student that should take the exam.
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ModelAndView SubmitScheduleRequestForm(@ModelAttribute Exam exam,
                                                  ModelAndView model) {
        model.setViewName("redirect:/instructor/schedule-event");
        model.addObject("heading", StringResources.INSTRUCTOR_SCHEDULE);
        if (!examDao.addExam(exam)) {
            model.addObject("errorMessage", "Could not add new exam.");
        }else {
            model.addObject("errorMessage", "Request submitted.");
        }
        return model;
    }
}
