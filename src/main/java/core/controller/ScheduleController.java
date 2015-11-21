package core.controller;

import core.event.Reservation;
import core.event.ReservationDao;
import core.controller.helper.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.Map;

@Controller
@RequestMapping("/schedule-event")
public class ScheduleController {
    @Autowired
    private ReservationDao reservationDao;

    @RequestMapping("/submit")
    public ModelAndView SubmitScheduleRequestForm(@RequestParam Map<String, Object> reservationParam) {
        ModelAndView model = new ModelAndView("instructor/include/schedule-event");
        String reservationId = new IdGenerator().generateReservationId(reservationParam);

        Reservation reservation = new Reservation(
                reservationId,
                (LocalDateTime)reservationParam.get("start_date_time"),
                (LocalDateTime)reservationParam.get("end_date_time"),
                (String)reservationParam.get("instructor_id"),
                (String)reservationParam.get("term")
        );

        if (reservationDao.insertReservation(reservation)) {
            model.addObject("message", "Schedule another event");
        } else {
            model.addObject("message", "Error: Schedule failure");
        }
        return model;
    }
}
