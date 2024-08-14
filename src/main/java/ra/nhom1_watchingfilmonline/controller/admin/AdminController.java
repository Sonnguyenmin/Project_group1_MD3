package ra.nhom1_watchingfilmonline.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/admin")
public class AdminController {

    @RequestMapping(value = "/loadAdmin")
    public String adminHome() {
        return "admin/index";
    }
}
