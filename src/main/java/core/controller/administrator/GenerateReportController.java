package core.controller.administrator;

import core.Report;
import core.event.Term;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/report")
public class GenerateReportController {
    @Autowired
    private Report report;

    @RequestMapping(value = "/term")
    public ModelAndView termHandler(@ModelAttribute Term selectedTerm,
                                    ModelAndView model) {
        model.setViewName("generate-report");
        model.addObject("reportContent", report.showTermReport(selectedTerm));
        return model;
    }
}