package core.controller.administrator;

import core.event.Term;
import core.event.TestingCenterInfo;
import core.service.TermManagerService;
import core.service.TestingCenterInfoRetrieval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * This controller class is created to solely handle modifications of
 * testing center information.
 */
@Controller
public class EditInfoController {
    @Autowired
    private TestingCenterInfoRetrieval infoRetrieval;
    @Autowired
    private TermManagerService termManager;

    @RequestMapping("/admin/edit-info/general-submit")
    public ModelAndView addInfoPhaseOne (@ModelAttribute TestingCenterInfo centerInfo,
                                         ModelAndView model) {
        infoRetrieval.updateTestingCenterInfo(centerInfo);
        model.setViewName("redirect:/admin/view-info");
        return model;
    }

    @RequestMapping("/admin/view-info/change-term")
    public ModelAndView changeViewTerm (@RequestParam("viewedTerm") int termId,
                                        ModelAndView model) {
        model.setViewName("admin-view-info");
        model.addObject("info", infoRetrieval.findByTerm(termId));
        model.addObject("terms", termManager.getAllPopulatedTerms());
        return model;
    }

    @RequestMapping("/admin/new-term")
    public ModelAndView newTermForm (ModelAndView model) {
        model.setViewName("admin-new-term");
        return model;
    }

    @RequestMapping("/admin/new-term/submit")
    public ModelAndView newTerm (@ModelAttribute Term term,
                                 ModelAndView model) {
        model.setViewName("redirect:/edit-info");
        term.setTermName(term.termIdToName(term.getTermId()));
        TestingCenterInfo info = new TestingCenterInfo();
        info.setTerm(term.getTermId());

        infoRetrieval.insertTestingCenterInfo(info);
        termManager.insertTerm(term);
        model.addObject("term", term);
        return model;
    }
}