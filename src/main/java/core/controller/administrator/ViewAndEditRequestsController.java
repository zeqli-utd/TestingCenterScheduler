package core.controller.administrator;

import core.event.dao.ExamDao;
import core.event.dao.ReservationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("admin/view-requests")
public class ViewAndEditRequestsController {
    @Autowired
    ExamDao examDao;

    /**
     * Approve a request
     * @param id
     * @return
     */
    @RequestMapping("approve/{id}")
    public String approveRequest(@PathVariable("id") String id) {
        examDao.approveExam(id);
        return "redirect:admin/view-requests";
    }

}
