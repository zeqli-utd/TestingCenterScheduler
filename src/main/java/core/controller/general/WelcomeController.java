package core.controller.general;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WelcomeController {
    @RequestMapping(method = RequestMethod.GET)
    public String welcome() {
        return "/WEB-INF/welcome.jsp";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
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
