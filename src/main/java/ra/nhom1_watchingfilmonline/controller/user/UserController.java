package ra.nhom1_watchingfilmonline.controller.user;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class UserController {
    @RequestMapping(value = "/loadUser")
    public String userHome(Model model) {
//        model.addAttribute("user",user);
        return "user/home";
    }

    @RequestMapping(value = "/profile")
    public String profileUser() {
        return "user/profile";
    }
}
