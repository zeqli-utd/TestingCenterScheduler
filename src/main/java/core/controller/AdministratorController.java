package core.controller;

import core.event.AppointmentDao;
import core.event.ReservationDao;
import core.event.TestingCenterInfo;
import core.service.TestingCenterInfoRetrieval;
import core.user.SessionProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdministratorController {
    @Autowired
    private TestingCenterInfoRetrieval infoRetrieval;
    @Autowired
    private ReservationDao reservationAccess;
    @Autowired
    private AppointmentDao appointmentDao;
    @Autowired
    private SessionProfile profile;
    
    private ModelAndView modelAndView = new ModelAndView();

    public AdministratorController() {

    }

    /**
     * this is a controller method for view and edit information
     * this functionality is for administrator only
     * @return
     */
    @RequestMapping(value = "edit-info")
    public ModelAndView viewCenterInfo() {
        modelAndView.clear();
        modelAndView.setViewName("edit-info");
        //set the heading to that of view information page
        modelAndView.addObject("page_heading",
                StringResources.ADMINISTRATOR_OPERATIONS.get("viewInfo"));
        //infoRetrieval method will get a Map of testing center information
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
        modelAndView.addObject("main_content", reservationAccess.findAll());
        return modelAndView;
    }

    @RequestMapping("admin-view-appointments")
    public ModelAndView viewAppointments() {
        String netId = profile.getUserId();
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

    @RequestMapping("generate-report")
    public ModelAndView generateReport() {
        modelAndView.clear();
        modelAndView.addObject("page_heading",
                StringResources.ADMINISTRATOR_OPERATIONS.get("generateReport"));
        return modelAndView;
    }
}