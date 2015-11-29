package core.controller.administrator;

import core.event.Term;
import core.event.TestingCenterInfo;
import core.service.TestingCenterInfoRetrieval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * This controller class is created to solely handle modifications of
 * testing center information.
 */
@Controller
@RequestMapping("admin/edit-info")
@SessionAttributes("newTerm")
public class EditInfoController {
    @Autowired
    private TestingCenterInfoRetrieval infoRetrieval;

    private String viewName = "admin/home";

    @RequestMapping("new-term")
    public ModelAndView declareNewTerm(ModelAndView model) {
        model.addObject("popup-content", "admin/include/popup/new-term");
        return model;
    }

    @RequestMapping(value = "new-term/submit", method = RequestMethod.POST)
    public String addNewTerm(@ModelAttribute("newTerm") Term term,
                             @RequestParam Map allParam,
                             ModelMap model,
                             RedirectAttributes redirectAttributes) {
        model.put("content", "/admin/include/add-term");
        redirectAttributes.addFlashAttribute("newTerm", term);
        return "redirect:/admin/home";
    }

    @RequestMapping(value = "new/submit", method = RequestMethod.POST)
    public ModelAndView newInfoSubmit(@ModelAttribute("newInfo") TestingCenterInfo info,
                                      @ModelAttribute("newTerm") Term term,
                                      RedirectAttributes redirectAttributes) {
        info.setTerm(term.getTermId());
        redirectAttributes.addFlashAttribute("newInfo", info);
        ModelAndView model = new ModelAndView("redirect:/admin/home");
        model.addObject("content", "admin/include/add-term-two");
        return model;
    }

    @RequestMapping("new-two")
    public String createNewInfoTwo(ModelMap model) {
        return "";
    }

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
                                    @DateTimeFormat(pattern = "MM/dd/yyyy") LocalDate closedDateEnd,
                               @ModelAttribute("term") Term term) {

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

        return viewName;
    }

    /**
     *
     * @param numSeats
     * @return
     */
    @RequestMapping(value = "num-of-seats/modify", method = RequestMethod.POST)
    public String modifyNumberOfSeats(@RequestParam("modify-seats") int numSeats) {
        return viewName;
    }

    /**
     *
     * @param numAside
     * @return
     */
    @RequestMapping(value = "set-aside/modify", method = RequestMethod.POST)
    public String modifySetAsideSeats(@RequestParam("modify-aside") int numAside) {

        return viewName;
    }

    /**
     *
     * @param gap
     * @return
     */
    @RequestMapping(value = "gap/modify", method = RequestMethod.POST)
    public String modifyGapTime(@RequestParam("gap-time") int gap) {

        return viewName;
    }

    /**
     *
     * @param interval
     * @return
     */
    @RequestMapping(value = "interval/modify", method = RequestMethod.POST)
    public String modifyReminderInterval(@RequestParam("modify-interval") int interval) {

        return viewName;
    }
}