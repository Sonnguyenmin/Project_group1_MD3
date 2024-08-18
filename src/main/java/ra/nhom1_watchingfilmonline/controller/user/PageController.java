package ra.nhom1_watchingfilmonline.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
// chi Vien lam de dieu huong trang nhe
@Controller
public class PageController {
    @GetMapping("/saveCurrentPage")
    public String saveCurrentPage(@RequestParam String path, HttpSession session) {
        session.setAttribute("currentPage", path);
        return "redirect:" + path;
    }
}
