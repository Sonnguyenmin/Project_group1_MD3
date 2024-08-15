package ra.nhom1_watchingfilmonline.controller.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ra.nhom1_watchingfilmonline.model.entity.Users;
import ra.nhom1_watchingfilmonline.service.IUserService;
import ra.nhom1_watchingfilmonline.service.impl.BannerService;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @Autowired
    public IUserService userService;
    @Autowired
    public BannerService bannerService;

    @RequestMapping(value = "/loadUser")
    public String userHome(Model model) {
        String currentUser = userService.getCurrentUserName();
        model.addAttribute("bannerList",bannerService.findAll());
        model.addAttribute("user", currentUser);
        return "user/home";
    }

    @RequestMapping(value = "/profile")
    public String profileUser() {
        return "user/profile";
    }

    @PostMapping("/changeProfile")
    public String updateProfile(@ModelAttribute("user") Users users, HttpSession session) {
        // Get the current user from the session
        Users currentUser = (Users) session.getAttribute("user");

        // Update the user's information
        currentUser.setUserName(users.getUserName());
        currentUser.setEmail(users.getEmail());
        currentUser.setFullName(users.getFullName());

        // Save the updated user information
        userService.save(currentUser);

        // Update the session with the new user information
        session.setAttribute("user", currentUser);

        return "redirect:/profile";
    }

    @GetMapping("/byVip")
    public String openByVip(){
        return "user/byVip";
    }
}
