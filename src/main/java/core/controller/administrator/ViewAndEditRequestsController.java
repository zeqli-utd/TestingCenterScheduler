package core.controller.administrator;

import core.event.dao.ReservationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/view-requests")
public class ViewAndEditRequestsController {
    @Autowired
    ReservationDao reservationDao;

    @RequestMapping("approve/{id}")
    public String approveRequest(@PathVariable("id") String id) {

        return "redirect:admin/view-requests";
    }
}
