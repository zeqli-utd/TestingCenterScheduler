package core.controller;

import core.event.AppointmentDao;
import core.event.ReservationDao;
import core.service.TestingCenterInfoRetrieval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StudentController {
    @Autowired
    private TestingCenterInfoRetrieval infoRetrieval;
    @Autowired
    private ReservationDao reservationAccess;
    @Autowired
    private AppointmentDao appointmentDao;

    private ModelAndView model = new ModelAndView();

    @RequestMapping("/student-view-appointments")
    public ModelAndView viewAppointments() {
        model.clear();
        model.setViewName("student-view-appointments");
        return model;
    }

    @RequestMapping("/student-make-appointment")
    public ModelAndView makeAppointment() {
        model.clear();
        model.setViewName("student-make-appointment");
        return model;
    }
}