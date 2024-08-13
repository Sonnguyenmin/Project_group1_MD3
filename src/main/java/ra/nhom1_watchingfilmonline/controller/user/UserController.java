package ra.nhom1_watchingfilmonline.controller.user;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class UserController {
    @RequestMapping(value = "/loadUser")
    public String userHome() {
        return "user/home";
    }

}
