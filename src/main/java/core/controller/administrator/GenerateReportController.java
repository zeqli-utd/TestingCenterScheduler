package core.controller.administrator;

import core.Report;
import core.event.Term;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/report")
public class GenerateReportController {

    @Autowired
    private Report report;

    @RequestMapping(value = "/term")
    @ResponseBody
    public String termHandler(@ModelAttribute Term selectedTerm) {
        return report.showTermReport(selectedTerm) +
                report.showWeekReport(selectedTerm) +
                report.showDayReport(selectedTerm);
    }
}