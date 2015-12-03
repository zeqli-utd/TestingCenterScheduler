package core.controller.administrator;

import core.event.dao.AppointmentDao;
import core.service.AppointmentManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CheckInController {
    @Autowired
    AppointmentManageService appointmentManager;
    @Autowired
    AppointmentDao appointmentDao;

    @RequestMapping("/admin/check-in/submit")
    public ModelAndView checkInStudent(@RequestParam("studentId") String studentId,
                                       ModelAndView model) {
        model.setViewName("check-in-appointments");
        model.addObject("appointments", appointmentDao.findAllByStudent(studentId));
        return model;
    }

    @RequestMapping("/admin/check-in/commit/{id}")
    public ModelAndView checkInStudentCommit(@PathVariable("id") int appointmentId,
                                             ModelAndView model) {
        model.setViewName("redirect:/admin/check-in");
        model.addObject("errorMessage", "Check in successful.");
        appointmentManager.checkinStudent(appointmentId);
        return model;
    }
}
