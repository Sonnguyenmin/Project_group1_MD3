package ra.nhom1_watchingfilmonline.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ra.nhom1_watchingfilmonline.model.entity.Users;
import ra.nhom1_watchingfilmonline.service.IUserService;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping(value = "/userManagement")
public class CustomerController {

    @Autowired
    private HttpSession session;


    @Autowired
    private IUserService userService;
//    @RequestMapping(value = "/loadAdmin")
//    public String adminHome() {
//        return "admin/index";
//    }
    @RequestMapping(value = "")
    public String userManagement(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @RequestParam(name = "search", defaultValue = "") String search,
            Model model) {
        List<Users> users = userService.findAllUsers(size, page, search);
        //set session
        session.setAttribute("active_user", "users");
        //set list film pagination
        model.addAttribute("users", userService.findAllUsers(page, size, search));
        model.addAttribute("page", page);
        model.addAttribute("search", search);
        model.addAttribute("size", size);
        //totalPages
        Double totalPages = Math.ceil((double) userService.totalAllUser(search) / size);
        model.addAttribute("totalPages", totalPages);
        return "admin/user/listUser";
    }

    @PostMapping("")
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

    @GetMapping("/sortUserList")
    public String sortByName(Model model, @RequestParam(value = "sort", defaultValue = "asc") String sort,
                             @RequestParam(name = "page", defaultValue = "0") Integer page,
                             @RequestParam(name = "size", defaultValue = "5") Integer size,
                             @RequestParam(name = "search", defaultValue = "") String search
    ) {
        List<Users> users;
        if ("asc".equalsIgnoreCase(sort)) {
            users = userService.findAllByOrderByUserDesc(page, size);
        } else {
            users = userService.findAllByOrderByUserAsc(page, size);
        }
        //totalPages
        Double totalPages = Math.ceil((double) userService.totalAllUser(search) / size);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("users", users);
        model.addAttribute("search", search);
        model.addAttribute("sort", sort);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        return "admin/user/listUser";
    }

}
