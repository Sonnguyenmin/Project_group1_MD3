package ra.nhom1_watchingfilmonline.controller.user;
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
public class UserController {
    @Autowired
    private IUserService userService;
    @RequestMapping(value = "/loadUser")

    public String userHome(Model model) {
        String currentUser = userService.getCurrentUserName();
        model.addAttribute("user", currentUser);
        return "user/home";
    }


}
