package ra.nhom1_watchingfilmonline.controller.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ra.nhom1_watchingfilmonline.model.entity.Users;
import ra.nhom1_watchingfilmonline.service.IUserService;
import ra.nhom1_watchingfilmonline.service.UploadService;
import ra.nhom1_watchingfilmonline.service.impl.BannerService;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @Autowired
    HttpSession session;
    @Autowired
    public IUserService userService;
    @Autowired
    public BannerService bannerService;
    @Autowired
    public UploadService uploadService;


    @RequestMapping(value = "/loadUser")
    public String userHome(Model model,HttpSession session) {
        Users userCurrent = (Users) session.getAttribute("userCurrent");
        model.addAttribute("userCurrent",userCurrent);
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
    public String updateProfile(
            @RequestParam("username") String userName,
            @RequestParam("email") String email,
            @RequestParam("fname") String fullName,
            @RequestParam("fileAvatar") MultipartFile fileAvatar,Model model
    ) {
        // Get the current user from the session

        Users currentUser = (Users) session.getAttribute("user");

        // Update the user's information
        currentUser.setUserName(userName);
        currentUser.setEmail(email);
        currentUser.setFullName(fullName);

        if (fileAvatar != null && !fileAvatar.isEmpty()) {
            String avatarUrl = uploadService.uploadFileToServer(fileAvatar);
            currentUser.setAvatar(avatarUrl);
        }
        // Save the updated user information
        userService.update(currentUser);

        // Update the session with the new user information
        session.setAttribute("user", currentUser);

        return "redirect:/profile";
    }

    @PostMapping("/changePassword")
    public String changePassword(
            @RequestParam("oldpass") String oldPass,
            @RequestParam("newpass") String newPass,
            Model model
    ) {
        // Get the current user from the session

        Users currentUser = (Users) session.getAttribute("user");

        // match old password -> change
        if (oldPass.equals(currentUser.getPassword())){
            currentUser.setPassword(newPass);
        }else {
            model.addAttribute("error", "Sai mật khẩu cũ");
            return "user/profile";
        }

        // Save the updated user information
        userService.update(currentUser);

        // Update the session with the new user information
        session.setAttribute("user", currentUser);

        return "redirect:/profile";
    }

    @GetMapping("/deposit")
    public String openDeposit(){
        return "user/deposit";
    }

    @PostMapping("/deposit")
    public String deposit(@RequestParam("money") Integer money, Model model)
    {
        Users user = (Users) session.getAttribute("user");
        if (money < 0)
        {
            model.addAttribute("error", "money must be than 0");
            return "user/deposit";
        }
        userService.handleAddWallet(user, money, session);
        return "redirect:/profile";

    }

    @GetMapping("/byVip")
    public String openByVip(){
        return "user/byVip";
    }
}
