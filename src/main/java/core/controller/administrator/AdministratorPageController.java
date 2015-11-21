package core.controller.administrator;

import core.controller.helper.StringResources;
import core.event.AppointmentDao;
import core.event.ReservationDao;
import core.event.TestingCenterInfo;
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
    private ReservationDao reservationDao;
    @Autowired
    private AppointmentDao appointmentDao;
    
    private ModelAndView modelAndView = new ModelAndView();

    public AdministratorPageController() {

    }

    private String includes (String fragment) {
        String prefix = "include/";
        String suffix = ".jsp";

        return prefix + fragment + suffix;
    }

    @RequestMapping("home")
    public ModelAndView goToHome() {
        modelAndView.clear();
        modelAndView.setViewName("admin/home");

        modelAndView.addObject("page_heading",
                StringResources.ADMINISTRATOR_OPERATIONS.get("home"));
        modelAndView.addObject("content", includes("home-content"));

        return modelAndView;
    }

    /**
     * this is a controller method for view and edit information
     * this functionality is for administrator only
     * @return modelAndView
     */
    @RequestMapping(value = "edit-info")
    public ModelAndView viewCenterInfo() {
        modelAndView.clear();
        modelAndView.setViewName("admin/home");

        modelAndView.addObject("page_heading",
                StringResources.ADMINISTRATOR_OPERATIONS.get("viewInfo"));
        modelAndView.addObject("content", includes("edit-info"));
        TestingCenterInfo centerInfo = infoRetrieval.retrieveInfo();
        modelAndView.addObject("centerInfo", centerInfo);

        return modelAndView;
    }

    /**
     * This method implements administrator's functionality for uploading
     * a file containing all users.
     * @return modelAndView
     */
    @RequestMapping("upload")
    public ModelAndView uploadFile() {
        modelAndView.clear();
        modelAndView.setViewName("admin/home");

        modelAndView.addObject("page_heading",
                StringResources.ADMINISTRATOR_OPERATIONS.get("uploadFile"));
        modelAndView.addObject("content", includes("upload"));

        return modelAndView;
    }

    @RequestMapping("view-requests")
    public ModelAndView viewRequests() {
        modelAndView.clear();
        modelAndView.setViewName("admin/home");

        modelAndView.addObject("page_heading",
                StringResources.ADMINISTRATOR_OPERATIONS.get("viewRequest"));
        /*by default, when the user enters the page all requests are displayed in chronological order
               by selecting different tabs on top of the list, the user is able to view the list
               in different orders: by alphabetical order of instructors' last names, number of attendants,
               utilization, or, display only the ones made by one instructor by searching the instructor's name*/
        modelAndView.addObject("content", includes("view-requests"));
        modelAndView.addObject("requests", reservationDao.findAll());

        return modelAndView;
    }

    @RequestMapping("view-appointments")
    public ModelAndView viewAppointments() {
        modelAndView.clear();
        modelAndView.setViewName("admin/home");

        modelAndView.addObject("page_heading",
                StringResources.ADMINISTRATOR_OPERATIONS.get("viewAppointments"));
        modelAndView.addObject("contents", includes("view-appointments"));
        modelAndView.addObject("appointments", appointmentDao.findAllAppointment());

        return modelAndView;
    }

    @RequestMapping("check-in")
    public ModelAndView checkIn() {
        modelAndView.clear();
        modelAndView.setViewName("admin/home");

        modelAndView.addObject("page_heading",
                StringResources.ADMINISTRATOR_OPERATIONS.get("checkIn"));
        modelAndView.addObject("content", includes("check-in"));

        return modelAndView;
    }

    @RequestMapping("make-appointment")
    public ModelAndView makeAppointment() {
        modelAndView.clear();
        modelAndView.setViewName("admin/home");

        modelAndView.addObject("page_heading",
                StringResources.ADMINISTRATOR_OPERATIONS.get("make-appointment"));
        modelAndView.addObject("content", includes("make-appointment"));

        return modelAndView;
    }

    @RequestMapping("generate-report")
    public ModelAndView generateReport() {
        modelAndView.clear();
        modelAndView.setViewName("admin/home");

        modelAndView.addObject("page_heading",
                StringResources.ADMINISTRATOR_OPERATIONS.get("generateReport"));
        modelAndView.addObject("content", includes("generate-report"));

        return modelAndView;
    }
}