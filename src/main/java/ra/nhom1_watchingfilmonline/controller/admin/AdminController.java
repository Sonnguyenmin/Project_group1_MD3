package ra.nhom1_watchingfilmonline.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ra.nhom1_watchingfilmonline.service.IUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller

public class AdminController {

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/loadAdmin")
    public String adminHome(Model model) {
        String currentUser = userService.getCurrentUserName();
        model.addAttribute("user", currentUser);
        return "admin/index";
    }

}
