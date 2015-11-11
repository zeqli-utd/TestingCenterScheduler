package core.controller;

import core.service.AuthenticationService;
import core.user.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController{
    @Autowired
    private AuthenticationService authenticationService;

    private ModelAndView model = new ModelAndView("login");

    @RequestMapping(value = "authorizing", method = RequestMethod.POST)
    public ModelAndView authorizeUser (@RequestParam("netId") String userId,
                                       @RequestParam("password") String password,
                                       HttpServletRequest request) {
        Authorization authorization = authenticationService.login(userId, password);
        request.getSession().setAttribute("sessionUserId", userId);
        if (authorization != null) {
            switch (authorization) {
                case STUDENT:
                    model.setViewName("student-home");
                    return model;
                case INSTRUCTOR:
                    model.setViewName("instructor-home");
                    return model;
                case ADMINISTRATOR:
                    model.setViewName("admin-home");
                    return model;
            }
        }else {
            if (authenticationService.registeredUserId(userId)) {
                model.addObject("errorMessage", StringResources.LOGIN_PASSWORD_ERROR);
            }else {
                model.addObject("errorMessage", StringResources.LOGIN_USER_ERROR);
            }
        }
        return model;
    }
}
