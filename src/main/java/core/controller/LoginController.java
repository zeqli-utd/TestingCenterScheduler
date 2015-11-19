package core.controller;

import core.service.AuthenticationService;
import core.user.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController{
    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(value = "authorizing")
    public String authorizeUser (@RequestParam("netId") String userId,
                                 @RequestParam("password") String password,
                                 HttpServletRequest request) {
        ModelMap model = new ModelMap();
        Authorization authorization = Authorization.ADMINISTRATOR;
        request.getSession().setAttribute("sessionUserId", userId);
        if (authorization != null) {
            switch (authorization) {
                case STUDENT:
                    return "student/home";
                case INSTRUCTOR:
                    return "instructor/home";
                case ADMINISTRATOR:
                    return "admin/home";
            }
        }else {
            if (authenticationService.registeredUserId(userId)) {
                model.addAttribute("errorMessage", StringResources.LOGIN_PASSWORD_ERROR);
                return "login";
            }else {
                model.addAttribute("errorMessage", StringResources.LOGIN_USER_ERROR);
                return "login";
            }
        }
        return "login";
    }
}