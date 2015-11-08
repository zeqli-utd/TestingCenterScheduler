package core.controller;

import core.service.AuthenticationService;
import core.user.Authorization;
import core.user.SessionProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController{
    @Autowired
    private AuthenticationService authenticationService;//injected service

    private ModelMap model = new ModelMap();
    @Autowired
    private SessionProfile profile;

    @RequestMapping(value = "authorizing", method = RequestMethod.POST)
    public String authorizeUser (@RequestParam("netId") String userId,
                                 @RequestParam("password") String password) {
        Authorization authorization = authenticationService.login(userId, password);
        if (authorization != null) {
            profile.setUserId(userId);
            switch (authorization) {
                case STUDENT:
                    return "student-home";
                case INSTRUCTOR:
                    return "instructor-home";
                case ADMINISTRATOR:
                    return "admin-home";
            }
        }else {
            if (authenticationService.registeredUserId(userId)) {
                model.addAttribute("errorMessage", StringResources.LOGIN_PASSWORD_ERROR);
            }else {
                model.addAttribute("errorMessage", StringResources.LOGIN_USER_ERROR);
            }
        }
        return "login";
    }
}
