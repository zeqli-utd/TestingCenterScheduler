package core.controller.administrator;

import core.event.Utilization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UtilizationController {
    @Autowired
    Utilization utilization;

    @RequestMapping("/admin/view-utilization/submit")
    public ModelAndView getUtil(@RequestParam("startDate") LocalDate startDate,
                                @RequestParam("endDate") LocalDate endDate,
                                ModelAndView model) {
        model.setViewName("util-get");
        List<Double> actualDates = new ArrayList<>();
        List<Double> expectedDates = new ArrayList<>();
        List<LocalDate> dates = new ArrayList<>();
        while (!startDate.isEqual(endDate)){
            actualDates.add(utilization.countUtilzActual(startDate));
            expectedDates.add(utilization.countUtilzExpection(startDate));
            dates.add(startDate);
            startDate.plusDays(1);
        }
        model.addObject("actualDates", actualDates);
        model.addObject("expectedDates", expectedDates);
        model.addObject("dates", dates);
        return model;
    }
}
