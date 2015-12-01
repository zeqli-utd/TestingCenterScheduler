package core.controller.instructor;

import core.event.AdhocExam;
import core.event.dao.AdhocExamDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ScheduleAdhoc {
    @Autowired
    AdhocExamDao adhocDao;

    @RequestMapping("/instructor/adhoc/submit")
    public ModelAndView SubmitAdhocRequest (@ModelAttribute AdhocExam adhocExam,
                                            ModelAndView model) {
        adhocDao.addAdhocExam(adhocExam);
        model.setViewName("redirect:/instructor/view-requests");
        return model;
    }
}
