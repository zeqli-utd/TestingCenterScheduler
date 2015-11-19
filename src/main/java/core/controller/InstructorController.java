package core.controller;

import core.event.AppointmentDao;
import core.event.ReservationDao;
import core.service.TestingCenterInfoRetrieval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class InstructorController {
    @Autowired
    private TestingCenterInfoRetrieval infoRetrieval;
    @Autowired
    private ReservationDao reservationAccess;
    @Autowired
    private AppointmentDao appointmentDao;

    private ModelAndView model = new ModelAndView();

    @RequestMapping("view-requests")
    public ModelAndView viewRequests() {
        model.clear();
        model.setViewName("view-requests");
        return model;
    }

    @RequestMapping("schedule-event")
    public ModelAndView scheduleEvent() {
        model.clear();
        model.setViewName("include/instructor/schedule-event");
        return model;
    }
}