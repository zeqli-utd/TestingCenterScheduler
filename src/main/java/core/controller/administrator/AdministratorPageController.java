package core.controller.administrator;

import core.event.Term;
import core.event.dao.AppointmentDao;
import core.event.dao.ExamDao;
import core.helper.StringResources;
import core.service.TermManagerService;
import core.service.TestingCenterInfoRetrieval;
import core.user.Authorization;
import core.user.SessionProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class AdministratorPageController {
    @Autowired
    private TestingCenterInfoRetrieval infoRetrieval;
    @Autowired
    private AppointmentDao appointmentDao;
    @Autowired
    private ExamDao examDao;
    @Autowired
    private TermManagerService termManager;

    @RequestMapping("/admin/home")
    public ModelAndView goToHome(HttpSession session) {
        Map<String, Object> sessionAttributes = (Map<String, Object>) session.getAttribute("sessionAttributes");
        SessionProfile profile = (SessionProfile) sessionAttributes.get("sessionUser");
        if (profile == null || profile.getAuthorization() != Authorization.ADMINISTRATOR) {
            return new ModelAndView("no-permission");
        }
        return new ModelAndView("admin-home");
    }

    /**
     * this is a controller method for view and edit information
     * this functionality is for administrator only
     * @return modelAndView
     */
    @RequestMapping("/admin/edit-info")
    public ModelAndView viewCenterInfo(ModelAndView model) {
        model.setViewName("edit-info");
        model.addObject("pageHeader", StringResources.ADMINISTRATOR_VIEW_INFO);
        Term term = infoRetrieval.getCurrentTerm();
        model.addObject("currentTerm", term);
        model.addObject("terms", termManager.getAllPopulatedTerms());
        model.addObject("centerInfo", infoRetrieval.findByTerm(term.getTermId()));
        return model;
    }

    @RequestMapping("/admin/view-info")
    public ModelAndView viewInfo (ModelAndView model) {
        model.setViewName("admin-view-info");
        model.addObject("info", infoRetrieval
                .findByTerm(infoRetrieval
                        .getCurrentTerm().getTermId()));
        model.addObject("terms", termManager.getAllPopulatedTerms());
        return model;
    }

    /**
     * This method implements administrator's functionality for uploading
     * a file containing all users.
     * @return modelAndView
     */
    @RequestMapping("/admin/upload")
    public ModelAndView uploadFile(ModelAndView model) {
        model.setViewName("upload");
        model.addObject("terms", termManager.getAllPopulatedTerms());
        return model;
    }

    @RequestMapping("/admin/view-requests")
    public ModelAndView viewRequests(ModelAndView model) {
        model.setViewName("admin-view-requests");
        model.addObject("requests", examDao.getAllPending());
        return model;
    }

    @RequestMapping("/admin/view-appointments")
    public ModelAndView viewAppointments(ModelAndView model) {
        model.setViewName("admin-view-appointments");
        model.addObject("appointments", appointmentDao.findAllAppointmentsByTerm(infoRetrieval.getCurrentTerm()));
        return model;
    }

    @RequestMapping("/admin/check-in")
    public ModelAndView checkIn(ModelAndView model) {
        model.setViewName("check-in");
        model.addObject("pageHeader", StringResources.ADMINISTRATOR_CHECK_IN);

        return model;
    }

    @RequestMapping("/admin/make-appointment")
    public ModelAndView makeAppointment(ModelAndView model) {
        model.setViewName("make-appointment");
        return model;
    }

    @RequestMapping("/admin/generate-report")
    public ModelAndView generateReport(ModelAndView model) {
        model.setViewName("report-term");
        model.addObject("terms", termManager.getAllPopulatedTerms());
        return model;
    }

    @RequestMapping("/admin/view-utilization")
    public ModelAndView viewUtilization (ModelAndView model) {
        model.setViewName("view-util");
        return model;
    }

    @RequestMapping("/admin/new-term")
    public ModelAndView newTerm(ModelAndView model) {
        model.setViewName("admin-new-term");
        return model;
    }
}