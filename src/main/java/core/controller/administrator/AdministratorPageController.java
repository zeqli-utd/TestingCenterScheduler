package core.controller.administrator;

import core.event.Term;
import core.event.dao.AppointmentDao;
import core.event.dao.ExamDao;
import core.helper.StringResources;
import core.service.TermManagerService;
import core.service.TestingCenterInfoRetrieval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView goToHome() {
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

    /**
     * This method implements administrator's functionality for uploading
     * a file containing all users.
     * @return modelAndView
     */
    @RequestMapping("/admin/upload")
    public ModelAndView uploadFile(ModelAndView model) {
        model.setViewName("upload");
        model.addObject("pageHeader", StringResources.ADMINISTRATOR_UPLOAD);
        model.addObject("terms", termManager.getAllPopulatedTerms());
        return model;
    }

    @RequestMapping("/admin/view-requests")
    public ModelAndView viewRequests(ModelAndView model) {
        model.setViewName("admin-view-requests");
        model.addObject("pageHeader", StringResources.ADMINISTRATOR_VIEW_REQUESTS);
        model.addObject("requests", examDao.getAllPending());
        return model;
    }

    @RequestMapping("/admin/view-appointments")
    public ModelAndView viewAppointments(ModelAndView model) {
        model.clear();
        model.setViewName("admin-view-appointments");
        model.addObject("pageHeader", StringResources.ADMINISTRATOR_VIEW_APPOINTMENTS);
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
        model.addObject("pageHeader", StringResources.ADMINISTRATOR_MAKE_APPOINTMENT);
        return model;
    }

    @RequestMapping("/admin/generate-report")
    public ModelAndView generateReport(ModelAndView model) {
        model.setViewName("report-term");
        model.addObject("pageHeader", StringResources.ADMINISTRATOR_REPORT);
        model.addObject("terms", termManager.getAllPopulatedTerms());
        return model;
    }
}