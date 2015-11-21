package core.controller.administrator;

import core.service.TestingCenterInfoRetrieval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * all actions concerning modifications of information
 * are directed to the methods in this controller class, including the
 * modifications and cancellations of requests, appointments,
 * as well as testing center information
 */
@Controller
@RequestMapping("admin/edit-info")
public class EditInfoController {
    @Autowired
    private TestingCenterInfoRetrieval infoRetrieval;
    
    private String viewName = "redirect:/admin/edit-info";
    
    /**
     * this method is called when a information cell is clicked and
     * the user is then allowed to change the contents of the fields of
     * testing center information. To prompt the user to change the selected
     * field, a popup window will appear, asking for input for the specified
     * field.
     * @param closedDateStart start of the closed dates
     * @param closedDateEnd end of the closed dates
     * @return view name
     */
    @RequestMapping(value = "closed-dates/add", method = RequestMethod.POST)
    public String addCloseDate(@RequestParam("add-closed-date-start")
                                    @DateTimeFormat(pattern = "MM/dd/yyyy") LocalDate closedDateStart,
                               @RequestParam("add-closed-date-start")
                                    @DateTimeFormat(pattern = "MM/dd/yyyy") LocalDate closedDateEnd) {
        infoRetrieval.addCloseDates(new LocalDate[]{closedDateStart, closedDateEnd});
        return viewName;
    }

    /**
     * The user adds a new reserved date range
     * @param reservedDateStart
     * @param reservedDateEnd
     * @return
     */
    @RequestMapping(value = "reserve-dates/add", method = RequestMethod.POST)
    public String addReservedDate(@RequestParam("add-reserved-date-start")
                                        @DateTimeFormat(pattern = "MM/dd/yyyy|HH:mm") LocalDateTime reservedDateStart,
                                  @RequestParam("add-reserved-date-end")
                                        @DateTimeFormat(pattern = "MM/dd/yyyy|HH:mm") LocalDateTime reservedDateEnd) {
        infoRetrieval.addReserveDateTimes(new LocalDateTime[]{reservedDateStart, reservedDateEnd});
        return viewName;
    }

    /**
     * the user's input is directed to this method and then redirected to the updated
     * edit-info page
     * @param openHour
     * @return
     */
    @RequestMapping(value = "open/modify", method = RequestMethod.POST)
    public String modifyOpenHour(@RequestParam("modify-open-hour")
                                       @DateTimeFormat(pattern = "HH:mm") LocalDate openHour) {
        infoRetrieval.updateField("open", openHour);
        return viewName;
    }

    /**
     *
     * @param closeHour
     * @return
     */
    @RequestMapping(value = "close/modify", method = RequestMethod.POST)
    public String modifyCloseHour(@RequestParam("modify-close-hour")
                                        @DateTimeFormat(pattern = "HH:mm") LocalDate closeHour) {
        infoRetrieval.updateField("close", closeHour);
        return viewName;
    }

    /**
     *
     * @param numSeats
     * @return
     */
    @RequestMapping(value = "num-of-seats/modify", method = RequestMethod.POST)
    public String modifyNumberOfSeats(@RequestParam("modify-seats") int numSeats) {
        infoRetrieval.updateField("numSeats", numSeats);
        return viewName;
    }

    /**
     *
     * @param numAside
     * @return
     */
    @RequestMapping(value = "set-aside/modify", method = RequestMethod.POST)
    public String modifySetAsideSeats(@RequestParam("modify-aside") int numAside) {
        infoRetrieval.updateField("numSetAsideSeats", numAside);
        return viewName;
    }

    /**
     *
     * @param gap
     * @return
     */
    @RequestMapping(value = "gap/modify", method = RequestMethod.POST)
    public String modifyGapTime(@RequestParam("gap-time") int gap) {
        infoRetrieval.updateField("gap", gap);
        return viewName;
    }

    /**
     *
     * @param interval
     * @return
     */
    @RequestMapping(value = "interval/modify", method = RequestMethod.POST)
    public String modifyReminderInterval(@RequestParam("modify-interval") int interval) {
        infoRetrieval.updateField("reminderInterval", interval);
        return viewName;
    }
}