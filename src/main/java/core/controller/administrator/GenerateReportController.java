package core.controller.administrator;

import core.Report;
import core.event.Term;
import core.helper.StringResources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/report")
public class GenerateReportController {
    @Autowired
    private Report report;

    @RequestMapping(value = "/term", method = RequestMethod.POST)
    public ModelAndView termHandler(@ModelAttribute("selectedTerm")Term term,
                                    ModelAndView model,
                                    RedirectAttributes attributes) {
        model.setViewName("redirect:/admin/generate-report");
        model.addObject("pageHeading", StringResources.ADMINISTRATOR_REPORT);
        attributes.addFlashAttribute("selectedTerm", term);
        return model;
    }

    @RequestMapping("/display")
    public ModelAndView getReport(ModelAndView model,
                                  RedirectAttributes attributes) {
        return null;
    }
}