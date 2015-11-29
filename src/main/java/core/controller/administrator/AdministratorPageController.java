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
@RequestMapping("/admin")
public class AdministratorPageController {
    @Autowired
    private TestingCenterInfoRetrieval infoRetrieval;
    @Autowired
    private AppointmentDao appointmentDao;
    @Autowired
    private ExamDao examDao;
    @Autowired
    private TermManagerService termManager;

    public AdministratorPageController() {

    }

    @RequestMapping("home")
    public ModelAndView goToHome(ModelAndView model) {
        model.setViewName("admin/home");
        model.addObject("pageHeader", "Home");
        return model;
    }

    /**
     * this is a controller method for view and edit information
     * this functionality is for administrator only
     * @return modelAndView
     */
    @RequestMapping(value = "edit-info")
    public ModelAndView viewCenterInfo(ModelAndView model) {
        model.setViewName("admin/edit-info");
        model.addObject("pageHeader", StringResources.ADMINISTRATOR_VIEW_INFO);
        Term term = infoRetrieval.getCurrentTerm();
        model.addObject("term", term);
        model.addObject("centerInfo", infoRetrieval.findByTerm(term.getTermId()));
        return model;
    }

    /**
     * This method implements administrator's functionality for uploading
     * a file containing all users.
     * @return modelAndView
     */
    @RequestMapping("upload")
    public ModelAndView uploadFile(ModelAndView model) {
        model.setViewName("admin/upload");
        model.addObject("pageHeader", StringResources.ADMINISTRATOR_UPLOAD);
        return model;
    }

    @RequestMapping("view-requests")
    public ModelAndView viewRequests(ModelAndView model) {
        model.setViewName("admin/view-requests");
        model.addObject("pageHeader", StringResources.ADMINISTRATOR_VIEW_REQUESTS);
        model.addObject("requests", examDao.getAllPending());
        return model;
    }

    @RequestMapping("view-appointments")
    public ModelAndView viewAppointments(ModelAndView model) {
        model.setViewName("admin/view-appointments");
        model.addObject("pageHeader", StringResources.ADMINISTRATOR_VIEW_APPOINTMENTS);
        model.addObject("appointments", appointmentDao.findAllAppointmentsByTerm(infoRetrieval.getCurrentTerm()));
        return model;
    }

    @RequestMapping("check-in")
    public ModelAndView checkIn(ModelAndView model) {
        model.setViewName("admin/check-in");
        model.addObject("pageHeader", StringResources.ADMINISTRATOR_CHECK_IN);

        return model;
    }

    @RequestMapping("make-appointment")
    public ModelAndView makeAppointment(ModelAndView model) {
        model.setViewName("admin/make-appointment");
        model.addObject("pageHeader", StringResources.ADMINISTRATOR_MAKE_APPOINTMENT);
        return model;
    }

    @RequestMapping("generate-report")
    public ModelAndView generateReport(ModelAndView model) {
        model.setViewName("admin/report-term");
        model.addObject("pageHeader", StringResources.ADMINISTRATOR_REPORT);
        model.addObject("terms", termManager.getAllPopulatedTerms());
        return model;
    }
}