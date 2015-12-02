package core.controller.administrator;

import core.event.TestingCenterInfo;
import core.service.TestingCenterInfoRetrieval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * This controller class is created to solely handle modifications of
 * testing center information.
 */
@Controller
@RequestMapping("admin/edit-info")
public class EditInfoController {

    @Autowired
    private TestingCenterInfoRetrieval infoRetrieval;

    @RequestMapping("general-submit")
    public ModelAndView addInfoPhaseOne (@ModelAttribute TestingCenterInfo centerInfo,
                                         ModelAndView model) {
        infoRetrieval.insertTestingCenterInfo(centerInfo);
        model.setViewName("redirect:/add-dates");
        return model;
    }
}