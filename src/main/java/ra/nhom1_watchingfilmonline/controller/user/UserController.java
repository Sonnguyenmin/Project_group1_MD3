package ra.nhom1_watchingfilmonline.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ra.nhom1_watchingfilmonline.service.impl.BannerService;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private BannerService bannerService;

    @GetMapping("")
    public String user(Model model){
        model.addAttribute("bannerList",bannerService.findAll());
        return "user/home";
    }
}
