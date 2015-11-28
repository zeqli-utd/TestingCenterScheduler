package core.controller.instructor;

import core.event.dao.ExamDao;
import core.helper.StringResources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/instructor/view-requests")
public class ViewRequestsController {
    @Autowired
    private ExamDao examDao;

    @RequestMapping("view-requests")
    public ModelAndView viewRequests(ModelAndView model) {
        model.setViewName("instructor/home");//find home.jsp-----view name
        model.addObject("content", "instructor/include/view-requests");//"content" is in .jsp file
        model.addObject("heading", StringResources.INSTRUCTOR_OPERATIONS.get("viewRequests"));

        model.addObject("requests", examDao.getAllPending());
        return model;
    }
}
