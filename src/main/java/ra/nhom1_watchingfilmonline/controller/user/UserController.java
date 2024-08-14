package ra.nhom1_watchingfilmonline.controller.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ra.nhom1_watchingfilmonline.service.IUserService;

@Controller
public class UserController {
    @Autowired
    public IUserService userService;
    @RequestMapping(value = "/loadUser")
    public String userHome(Model model) {

        String currentUser = userService.getCurrentUserName();
        model.addAttribute("user", currentUser);

        return "user/home";
    }

    @RequestMapping(value = "/profile")
    public String profileUser() {
        return "user/profile";
    }
}
