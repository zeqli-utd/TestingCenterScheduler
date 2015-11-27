package core.controller.instructor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/schedule-event")
public class ScheduleController {

    @RequestMapping("/submit")
    public ModelAndView SubmitScheduleRequestForm(@RequestParam Map<String, Object> allParam,
                                                  ModelAndView model) {
        model.setViewName("/instructor/home");

        return model;
    }
}
