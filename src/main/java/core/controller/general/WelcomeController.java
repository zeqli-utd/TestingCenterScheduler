package core.controller.general;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {
    @RequestMapping("/")
    public String welcome() {
        return "welcome";
    }

    @RequestMapping("login")
    public String welcomeToLogin() {
        return "login";
    }

    @RequestMapping("login/error")
    public String errorLogin(ModelMap modelMap) {
        modelMap.addAttribute("errorMessage", "Please check login credentials");
        return "login";
    }

    @RequestMapping("login/not-found")
    public String errorNotFound() {
        return "not-found";
    }
}
