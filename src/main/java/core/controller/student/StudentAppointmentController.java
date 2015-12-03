package core.controller.student;

import core.MakeAppointmentException;
import core.event.Appointment;
import core.event.TestingCenterTimeSlots;
import core.event.dao.ExamDao;
import core.event.dao.TestingCenterTimeSlotsDao;
import core.service.AppointmentManageService;
import core.user.SessionProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/student/make-appointment")
public class StudentAppointmentController {
    @Autowired
    private AppointmentManageService appointmentManageService;
    @Autowired
    private TestingCenterTimeSlotsDao timeSlotsDao;
    @Autowired
    private ExamDao examDao;

    @RequestMapping("new/{id}")
    public ModelAndView makeAppointment(@PathVariable("id") String examId,
                                        ModelAndView model){
        model.setViewName("select-appointment");
        model.addObject("exam", examDao.findByExamId(examId).getExamName());
        model.addObject("timeSlots", timeSlotsDao.findAllTimeSlotsByExamId(examId));
        return model;
    }

    @RequestMapping("commit/{id}")
    public ModelAndView commitAppointment(@PathVariable("id") String slotId,
                                          HttpSession session,
                                          ModelAndView model) {
        SessionProfile profile = (SessionProfile) session.getAttribute("sessionUser");

        Appointment appointment = new Appointment();
        TestingCenterTimeSlots slot = timeSlotsDao.findTimeSlotById(slotId);

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
        try{
            appointmentManageService.makeAppointment(appointment, slot);
        }catch (MakeAppointmentException e){
            String msg = e.getMessage();
        }
        return model;
    }
}