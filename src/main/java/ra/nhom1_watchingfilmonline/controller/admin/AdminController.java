package ra.nhom1_watchingfilmonline.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ra.nhom1_watchingfilmonline.service.ICategoriesService;
import ra.nhom1_watchingfilmonline.service.IUserService;

@Controller

public class AdminController {
    @Autowired
    public IUserService userService;

    @Autowired
    private ICategoriesService categoriesService;

    @RequestMapping(value = "/loadAdmin")
    public String adminHome(Model model) {

        String currentUser = userService.getCurrentUserName();
        model.addAttribute("categoryList",categoriesService.findAll());
        model.addAttribute("userList",userService.findAllUsers());
        model.addAttribute("user", currentUser);
        return "admin/index";
    }
}
