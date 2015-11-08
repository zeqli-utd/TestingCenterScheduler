package core.controller;

import core.event.AppointmentDao;
import core.event.ReservationDao;
import core.service.TestingCenterInfoRetrieval;
import core.user.SessionProfile;
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
    @Autowired
    private SessionProfile profile;

    private ModelAndView model = new ModelAndView();

    @RequestMapping("/INSTRUCTOR/view-requests")
    public ModelAndView viewRequests() {
        model.clear();
        model.setViewName("instructor-view-requests");
        return model;
    }

    @RequestMapping("/INSTRUCTOR/schedule-event")
    public ModelAndView scheduleEvent() {
        model.clear();
        model.setViewName("instructor-schedule-event");
        return model;
    }
}