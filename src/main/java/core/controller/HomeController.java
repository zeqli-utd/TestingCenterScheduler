package core.controller;

import core.user.Authorization;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping("{permission}/home")
    public String goToHome (@PathVariable("permission") Authorization authorization) {
        switch (authorization) {
            case ADMINISTRATOR:
                return "admin-home";
            case STUDENT:
                return "student-home";
            case INSTRUCTOR:
                return "instructor-home";
        }
        return null;
    }
}
