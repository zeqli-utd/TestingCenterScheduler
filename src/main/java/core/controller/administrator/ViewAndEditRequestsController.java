package core.controller.administrator;

import core.event.Exam;
import core.event.ExamStatusType;
import core.event.dao.ExamDao;
import core.helper.StringResources;
import core.service.EmailService;
import core.service.ExamManageService;
import core.user.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;

@Controller
@RequestMapping("/admin/view-requests")
public class ViewAndEditRequestsController {
    @Autowired
    ExamManageService examManager;
    @Autowired
    ExamDao examDao;
    @Autowired
    EmailService emailService;
    @Autowired
    UserDao userDao;

    @RequestMapping("approve/{id}")
    public ModelAndView approveRequest(@PathVariable("id") String id,
                                       ModelAndView model) {
        examManager.approveExam(id);
        model.setViewName("redirect:/admin/view-requests");

        String message = "Your request has been approved by the administrator.";
        String subject = "Request Approved";
        String address = userDao
                .getUserById(examDao
                        .findByExamId(id)
                        .getInstructorId()).getEmail();

        try {
            emailService.sendEmail(StringResources.EMAIL_HOST,
                    StringResources.EMAIL_PORT,
                    StringResources.EMAIL_LOGIN,
                    StringResources.EMAIL_PASSWORD,
                    address, subject,message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return model;
    }

    @RequestMapping("reject/{id}")
    public ModelAndView rejectRequest(@PathVariable("id") String examId,
                                      ModelAndView model) {
        Exam exam = examDao.findByExamId(examId);
        exam.setStatusType(ExamStatusType.DENIED);
        examDao.updateExam(exam);
        model.setViewName("redirect:/admin/view-requests");

        String message = "Your request has been reject by the administrator.";
        String subject = "Request Reject";
        String address = userDao
                .getUserById(examDao
                        .findByExamId(examId)
                        .getInstructorId()).getEmail();

        try {
            emailService.sendEmail(StringResources.EMAIL_HOST,
                    StringResources.EMAIL_PORT,
                    StringResources.EMAIL_LOGIN,
                    StringResources.EMAIL_PASSWORD,
                    address, subject,message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return model;
    }
}