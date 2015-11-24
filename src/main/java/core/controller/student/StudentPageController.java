package core.controller.student;

import core.event.dao.AppointmentDao;
import core.event.dao.CourseDao;
import core.event.dao.ExamDao;
import core.event.dao.ReservationDao;
import core.service.TestingCenterInfoRetrieval;
import core.user.dao.InstructorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StudentPageController {
    @Autowired
    private TestingCenterInfoRetrieval infoRetrieval;
    @Autowired
    private ReservationDao reservationAccess;
    @Autowired
    private AppointmentDao appointmentDao;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private InstructorDao instructorDao;
    @Autowired
    private ExamDao examDao;

    private ModelAndView model = new ModelAndView();

    @RequestMapping("/student-view-appointments")
    public ModelAndView viewAppointments() {
        model.clear();
        model.setViewName("student/include/view-appointments");
        return model;
    }

    @RequestMapping("/student-make-appointment")
    public ModelAndView makeAppointment() {
        model.clear();
        model.setViewName("student/home");
        model.addObject("content", "include/make-appointment");
        model.addObject("exams", examDao.getAllExams());
        return model;
    }
}