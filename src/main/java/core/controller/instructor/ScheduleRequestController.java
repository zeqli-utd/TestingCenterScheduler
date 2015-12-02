package core.controller.instructor;

import core.event.Exam;
import core.event.ExamType;
import core.event.dao.ExamDao;
import core.service.ExamManageService;
import core.user.SessionProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/instructor/schedule-event")
public class ScheduleRequestController {
    @Autowired
    ExamManageService examManageService;

    @RequestMapping(value = "/submit")
    public ModelAndView SubmitScheduleRequestForm(@ModelAttribute Exam exam,
                                                  ModelAndView model) {
    //TODO Need Profile to send notification email
//        SessionProfile profile = (SessionProfile) session.getAttribute("sessionUser");

        exam.setExamType(ExamType.REGULAR);
        model.setViewName("redirect:/instructor/view-requests");

//        if (!examManageService.addExam(exam, profile)) {
//            model.addObject("errorMessage", "Could not add new exam.");
//        }else {
//            model.addObject("errorMessage", "Request submitted.");
//        }
        return model;
    }
}
