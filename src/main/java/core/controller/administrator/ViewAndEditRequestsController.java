package core.controller.administrator;

import core.event.dao.ExamDao;
import core.service.ExamManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/view-requests")
public class ViewAndEditRequestsController {

    @Autowired
    ExamManageService examManageService;

    @RequestMapping("approve/{id}")
    public String approveRequest(@PathVariable("id") String id) {
        examManageService.approveExam(id);
        return "redirect:admin/view-requests";
    }

}
