package core.controller;

import core.event.Reservation;
import core.event.ReservationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class ScheduleController {
    @Autowired
    private ReservationDao reservationDao;

    @RequestMapping("schedule-event/submit")
    public ModelAndView SubmitScheduleRequestForm(@RequestParam Map<String, Object> scheduleParams) {
        ModelAndView model = new ModelAndView("schedule-event");
        model.addObject("message", "Schedule another event.");

        Reservation reservation;
        reservation = new Reservation();

        reservationDao.insertReservation(reservation);

        return model;
    }
}
