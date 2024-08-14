package ra.nhom1_watchingfilmonline.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ra.nhom1_watchingfilmonline.model.entity.Users;
import ra.nhom1_watchingfilmonline.service.IUserService;

import java.util.List;


@Controller
public class CustomerController {

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/userManagement")
    public String userManagement(Model model) {
        List<Users> users = userService.findAllUsers();
        model.addAttribute("user", users);
        return "admin/user/listUser";
    }

}
