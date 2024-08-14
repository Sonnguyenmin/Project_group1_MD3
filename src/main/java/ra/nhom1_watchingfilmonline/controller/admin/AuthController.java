package ra.nhom1_watchingfilmonline.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ra.nhom1_watchingfilmonline.model.dto.UserDto;
import ra.nhom1_watchingfilmonline.model.entity.Users;
import ra.nhom1_watchingfilmonline.service.FilmService;
import ra.nhom1_watchingfilmonline.service.ICategoriesService;
import ra.nhom1_watchingfilmonline.service.IUserService;
import ra.nhom1_watchingfilmonline.service.impl.BannerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
public class AuthController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ICategoriesService categoriesService;

    @Autowired
    private FilmService filmService;
    @Autowired
    private BannerService bannerService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @RequestMapping(value = {"/", "/loadHome"})
    public String mainHome(HttpSession session , @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "5") Integer size) {
        session.setAttribute("category",categoriesService.findAll());
        session.setAttribute("films", filmService.findAll());
        session.setAttribute("bannerList", bannerService.findAll());
        return "main/index";
    }

    // Trang đăng ký
    @GetMapping("/register")
    public String register(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user",user);
        return "page/register";
    }

    @PostMapping("/insertRegister")
    public String saveRegister(@Valid @ModelAttribute("user") UserDto user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "page/register";  // Trả về trang đăng ký nếu có lỗi
        }

        // Kiểm tra xem mật khẩu và xác nhận mật khẩu có khớp không
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            model.addAttribute("errorMessage", "Mật khẩu và xác nhận mật khẩu không khớp");
            return "page/register";
        }

        Integer roleId = 2; // Mặc định là 2 (user)

        // Kiểm tra email và phone để phân loại admin
        if ("admin123@gmail.com".equals(user.getEmail())) {
            roleId = 1; // Admin
        }

        // Kiểm tra xem người dùng đã tồn tại chưa
        Users existingUser = userService.findByOrEmailOrPhone(user.getEmail(), user.getPhone());
        if (existingUser != null) {
            result.rejectValue("email", "error.user", "Email hoặc số điện thoại đã được sử dụng");
            return "page/register";
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());  // Mã hóa mật khẩu

        userService.registerUser(user.getUserName(), user.getFullName(), user.getEmail(), user.getPhone(), encodedPassword, roleId);

        return "/page/login";  // Chuyển hướng đến trang đăng nhập
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        System.out.println("Login attempt");
        Users user = new Users();
        model.addAttribute("user", user);
        return "page/login";
    }
    @PostMapping("/insertLogin")
    public String handleLogin(@Valid @ModelAttribute("user") Users user,
                              @RequestParam("email") String email,
                              @RequestParam("password") String password,
                              HttpSession session, Model model) {

        System.out.println("Nhận yêu cầu đăng nhập với email: " + email);
        user = userService.findUsersByUsername(email);
        System.out.println("User found: " + (user != null));

        if (user != null) {
            boolean passwordMatches = passwordEncoder.matches(password, user.getPassword());
            System.out.println("Password matches: " + passwordMatches);

            if (passwordMatches) {
                session.setAttribute("user", user);

                if (user.getRoles().stream().anyMatch(role -> "ADMIN".equals(role.getRoleName()))) {
                    return "redirect:/loadAdmin";
                } else {
                    return "redirect:/loadUser";
                }
            } else {
                model.addAttribute("error", "Invalid email or password");
            }
        } else {
            model.addAttribute("error", "User not found");
        }
        return "page/login";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/"; //
    }
}
