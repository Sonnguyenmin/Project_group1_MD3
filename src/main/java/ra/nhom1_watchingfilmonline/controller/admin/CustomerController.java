package ra.nhom1_watchingfilmonline.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ra.nhom1_watchingfilmonline.model.entity.Categories;
import ra.nhom1_watchingfilmonline.model.entity.Users;
import ra.nhom1_watchingfilmonline.service.IUserService;

import java.util.List;


@Controller
public class CustomerController {

    @Autowired
    private IUserService userService;
//    @RequestMapping(value = "/loadAdmin")
//    public String adminHome() {
//        return "admin/index";
//    }
    @RequestMapping(value = "/userManagement")
    public String userManagement(Model model) {
        List<Users> users = userService.findAllUsers();
        model.addAttribute("user", users);
        return "admin/user/listUser";
    }

    @PostMapping("/userManagement")
    public String userStatusChange(@RequestParam("id") Integer id,
                                       @RequestParam("status") Boolean status,
                                       Model model) {
        try {
            // Tìm người dùng theo ID
            Users existingUser = userService.findUserById(id);
            if (existingUser == null) {
                model.addAttribute("errorMessage", "Ngươi dùng không tồn tại.");
                return "redirect:/userManagement";
            } else {
                existingUser.setStatus(!status);
                userService.update(existingUser);
                return "redirect:/userManagement";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Lỗi khi cập nhật trạng thái người dùng: " + e.getMessage());
            return "redirect:/userManagement";
        }
    }

}
