package core.controller.instructor;

import core.event.dao.ExamDao;
import core.helper.StringResources;
import core.user.SessionProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/instructor/view-requests")
public class ViewRequestsController {
    @Autowired
    private ExamDao examDao;

    @RequestMapping("/")
    public ModelAndView viewRequests(ModelAndView model) {
        model.setViewName("instructor-view-requests");
        model.addObject("heading", StringResources.INSTRUCTOR_VIEW_REQUESTS);
        model.addObject("requests", examDao.getAllPending());
        return model;
    }

    @RequestMapping(value = "/cancel/{id}", method = RequestMethod.POST)
    public ModelAndView cancelRequest(@PathVariable("id") String examId,
                                      HttpSession session,
                                      ModelAndView model) {
        model.setViewName("redirect:/instructor/view-requests");
        examDao.deleteExam(examId);
        model.addObject("heading", StringResources.INSTRUCTOR_VIEW_REQUESTS);
        SessionProfile profile = (SessionProfile) session.getAttribute("sessionUser");
        model.addObject("requests", examDao.findByInstructorId(profile.getUserId()));
        model.addObject("popupMessage", "Request cancelled");
        return model;
    }
}