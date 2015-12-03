package core.controller.administrator;

import core.event.Appointment;
import core.event.TestingCenterTimeSlots;
import core.event.dao.AppointmentDao;
import core.event.dao.ExamDao;
import core.event.dao.TestingCenterTimeSlotsDao;
import core.service.AppointmentManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
public class ViewAndEditAppointmentsController {
    @Autowired
    ExamDao examDao;
    @Autowired
    AppointmentDao appointmentDao;
    @Autowired
    TestingCenterTimeSlotsDao slotsDao;
    @Autowired
    AppointmentManageService appointmentManageService;

    @RequestMapping("/admin/make-appointment/student")
    public ModelAndView createAppointment(@RequestParam("studentId") String studentId,
                                          HttpSession session,
                                          ModelAndView model) {
        model.setViewName("make-appointment-exams");
        model.addObject("exams", examDao.getAllAvailableExamsToStudent(studentId));
        HashMap<String, Object> sessionAttributes = (HashMap<String, Object>) session.getAttribute("sessionAttributes");
        sessionAttributes.put("studentId", studentId);
        session.setAttribute("sessionAttributes", sessionAttributes);
        return model;
    }

    @RequestMapping("/admin/make-appointment/select/{id}")
    public ModelAndView selectExam(@PathVariable("id") String examId,
                                   ModelAndView model) {
        model.setViewName("admin-slots");
        model.addObject("slots", slotsDao
                .findAllTimeSlotsByExamId(examId));
        model.addObject("exam", examDao
                .findByExamId(examId).getExamName());
        return model;
    }

    @RequestMapping("/admin/make-appointment/commit/{id}")
    public ModelAndView commitAppointment(@PathVariable("id") String slotId,
                                          HttpSession session,
                                          ModelAndView model) {

        HashMap<String, Object> sessionAttributes = (HashMap<String, Object>) session.getAttribute("sessionAttributes");

        model.setViewName("redirect:/admin/view-appointments");
        Appointment appointment = new Appointment();
        TestingCenterTimeSlots slot = slotsDao.findTimeSlotById(slotId);
        appointment.setExamId(slot.getExamId());
        appointment.setStartDateTime(slot.getBegin());
        appointment.setEndDateTime(slot.getEnd());
        appointment.setStudentId((String) sessionAttributes.get("studentId"));
        appointment.setExamName
                (examDao.findByExamId
                        (slot.getExamId()).getExamName());
        appointment.setTerm
                (examDao.findByExamId
                        (appointment.getExamId()).getTerm());

        appointmentManageService.makeAppointment(appointment, slot);
        return model;
    }
}
