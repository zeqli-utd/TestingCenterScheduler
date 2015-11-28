package core.controller;

import core.helper.StringResources;
import core.service.AuthenticationService;
import core.user.Authorization;
import core.user.SessionProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController{
    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(value = "authorizing")
    public ModelAndView authorizeUser (@RequestParam("userId") String userId,
                                       @RequestParam("password") String password,
                                       @ModelAttribute("sessionUser")SessionProfile profile,
                                       ModelAndView model) {
        Authorization authorization = Authorization.ADMINISTRATOR;
        if (authorization != null) {
            switch (authorization) {
                case STUDENT:
                    model.setViewName("student/home");
                case INSTRUCTOR:
                    model.setViewName("instructor/home");
                case ADMINISTRATOR:
                    model.setViewName("admin/home");
            }
        }else {
            if (authenticationService.registeredUserId(userId)) {
                model.addObject("errorMessage", StringResources.LOGIN_PASSWORD_ERROR);
                model.setViewName("login");
            }else {
                model.addObject("errorMessage", StringResources.LOGIN_USER_ERROR);
                model.setViewName("login");
            }
        }
        return model;
    }
}