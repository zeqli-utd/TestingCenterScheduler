package core.controller.student;

import core.event.Appointment;
import core.event.TestingCenterTimeSlots;
import core.event.dao.AppointmentDao;
import core.event.dao.ExamDao;
import core.event.dao.TestingCenterTimeSlotsDao;
import core.helper.StringResources;
import core.user.SessionProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/student/make-appointment")
@SessionAttributes("sessionUser")
public class StudentAppointmentController {
    @Autowired
    private AppointmentDao appointmentDao;
    @Autowired
    private TestingCenterTimeSlotsDao timeSlotsDao;
    @Autowired
    private ExamDao examDao;

    @RequestMapping("new/{id}")
    public ModelAndView makeAppointment(@PathVariable("id") String examId,
                                        ModelAndView model){
        model.setViewName("/student/select-appointment");
        model.addObject("exam", examDao.findByExamId(examId).getExamName());
        model.addObject("heading", StringResources.STUDENT_MAKE_APPOINTMENT);
        model.addObject("timeSlots", timeSlotsDao.findAllTimeSlotsByExamId(examId));
        return model;
    }

    @RequestMapping(value = "submit", method = RequestMethod.POST)
    public ModelAndView selectAppointment(@ModelAttribute Appointment appointment,
                                          @ModelAttribute("sessionUser") SessionProfile profile,
                                          ModelAndView model) {
        model.setViewName("/student/view-appointments");
        model.addObject("heading", StringResources.STUDENT_VIEW_APPOINTMENTS);
        model.addObject("errorMessage", "Appointment submitted.");
        TestingCenterTimeSlots slot
                = timeSlotsDao.findTimeSlotById(appointment.getSlotId());
        appointment.setExamId(slot.getExamId());
        appointment.setStartDateTime(slot.getBegin());
        appointment.setEndDateTime(slot.getEnd());
        appointment.setStudentId(profile.getUserId());
        appointment.setExamName
                (examDao.findByExamId
                        (slot.getExamId()).getExamName());
        appointment.setTerm
                (examDao.findByExamId
                        (appointment.getExamId()).getTerm());

        appointmentDao.insertAppointment(appointment);
        return model;
    }
}