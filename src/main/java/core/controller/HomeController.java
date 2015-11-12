package core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping("admin-home")
    public String goToAdminHome() {
        return "admin-home";
    }

    @RequestMapping("instructor-home")
    public String goToInstructorHome() {
        return "instructor-home";
    }

    @RequestMapping("student-home")
    public String goToStudentHome() {
        return "student-home";
    }
}
