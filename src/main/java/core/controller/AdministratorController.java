package core.controller;

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
public class AdministratorController {
    @Autowired
    private TestingCenterInfoRetrieval infoRetrieval;
    @Autowired
    private ReservationDao reservationDao;
    @Autowired
    private AppointmentDao appointmentDao;
    
    private ModelAndView modelAndView = new ModelAndView();

    public AdministratorController() {

    }

    @RequestMapping("home")
    public ModelAndView goToHome() {
        modelAndView.clear();
        modelAndView.setViewName("admin/home");
        modelAndView.addObject("page_heading",
                StringResources.ADMINISTRATOR_OPERATIONS.get("home"));
        modelAndView.addObject("content", "home-content.jsp");
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
        modelAndView.setViewName("admin/include/edit-info");
        modelAndView.addObject("page_heading",
                StringResources.ADMINISTRATOR_OPERATIONS.get("viewInfo"));
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
        modelAndView.addObject("page_heading",
                StringResources.ADMINISTRATOR_OPERATIONS.get("uploadFile"));
        modelAndView.setViewName("admin/include/upload");
        return modelAndView;
    }

    @RequestMapping("admin-view-requests")
    public ModelAndView viewRequests() {
        modelAndView.clear();
        modelAndView.addObject("page_heading",
                StringResources.ADMINISTRATOR_OPERATIONS.get("viewRequest"));
        /*by default, when the user enters the page all requests are displayed in chronological order
               by selecting different tabs on top of the list, the user is able to view the list
               in different orders: by alphabetical order of instructors' last names, number of attendants,
               utilization, or, display only the ones made by one instructor by search the instructor's name*/
        modelAndView.addObject("main_content", reservationDao.findAll());
        modelAndView.setViewName("admin/include/view-requests");
        return modelAndView;
    }

    @RequestMapping("admin-view-appointments")
    public ModelAndView viewAppointments() {
        modelAndView.clear();
        modelAndView.addObject("page_heading",
                StringResources.ADMINISTRATOR_OPERATIONS.get("viewAppointments"));
        modelAndView.addObject("main_content", appointmentDao.findAllAppointment());
        return modelAndView;
    }

    @RequestMapping("check-in")
    public ModelAndView checkIn() {
        modelAndView.clear();
        modelAndView.addObject("page_heading",
                StringResources.ADMINISTRATOR_OPERATIONS.get("checkIn"));
        return modelAndView;
    }

    @RequestMapping("make-appointment")
    public ModelAndView makeAppointment() {
        modelAndView.clear();

        return modelAndView;
    }

    @RequestMapping("generate-report")
    public ModelAndView generateReport() {
        modelAndView.clear();
        modelAndView.addObject("page_heading",
                StringResources.ADMINISTRATOR_OPERATIONS.get("generateReport"));
        return modelAndView;
    }
}