package core.controller.administrator;

import core.event.dao.AppointmentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("admin")
public class ViewAndEditAppointmentsController {
    @Autowired
    AppointmentDao appointmentDao;

    @RequestMapping(value = {"make-appointment/create", "view-appointment#new/create"},
            method = RequestMethod.POST)
    public String createAppointment() {

        return "redirect:admin/view-appointments";
    }

    @RequestMapping(value = "view-appointment/cancel/{id}",
            method = RequestMethod.POST)
    public String cancelAppointment(@PathVariable("id") String appointmentId) {
        appointmentDao.deleteAppointment(appointmentId);

        return "redirect:admin/view-appointment";
    }
}
