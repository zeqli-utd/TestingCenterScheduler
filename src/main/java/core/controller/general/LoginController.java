package core.controller.general;

import core.service.AuthenticationService;
import core.user.SessionProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(value = "authorizing")
    public ModelAndView authorizeUser (@ModelAttribute SessionProfile profile,
                                       HttpSession session,
                                       ModelAndView model) {
        model.setViewName("instructor/home");
        model.addObject("pageHeader", "Administrator");
        //String userId = profile.getUserId();
        //String password = profile.getPassword();
        //
        //if (!authenticationService.registeredUserId(userId)) {
        //    model.addObject("errorMessage", "User does not exist");
        //    model.setViewName("login");
        //    return model;
        //}
        //
        //if (!authenticationService.userMatchPassword(userId, password)) {
        //    model.addObject("errorMessage", "This Combination does not exist");
        //    model.setViewName("login");
        //    return model;
        //}
        //
        //Authorization authorization = authenticationService.login(userId);
        //if (authorization != null) {
        //    switch (authorization) {
        //        case STUDENT:
        //            model.setViewName("redirect:/student/home");
        //            break;
        //        case INSTRUCTOR:
        //        case INSTRUCTOR_STUDENT:
        //            model.setViewName("redirect:/instructor/home");
        //            break;
        //        case ADMINISTRATOR:
        //        case ADMINISTRATOR_INSTRUCTOR:
        //        case ADMINISTRATOR_STUDENT:
        //        case TRINITY:
        //            model.setViewName("redirect:/admin/home");
        //            break;
        //    }
        //}else {
        //    if (authenticationService.registeredUserId(userId)) {
        //        model.addObject("errorMessage", StringResources.LOGIN_PASSWORD_ERROR);
        //        model.setViewName("redirect:/login");
        //    }else {
        //        model.addObject("errorMessage", StringResources.LOGIN_USER_ERROR);
        //        model.setViewName("redirect:/login");
        //    }
        //}
        //profile.setAuthorization(authorization);
        //session.setAttribute("sessionUser", profile);
        return model;
    }
}