package ra.nhom1_watchingfilmonline.controller.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ra.nhom1_watchingfilmonline.model.dto.FilmDto;
import ra.nhom1_watchingfilmonline.model.entity.*;
import ra.nhom1_watchingfilmonline.service.FilmService;
import ra.nhom1_watchingfilmonline.service.ICategoriesService;
import ra.nhom1_watchingfilmonline.service.ICommentService;
import ra.nhom1_watchingfilmonline.service.IUserService;
import ra.nhom1_watchingfilmonline.service.impl.CountryService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private FilmService filmService;
    @Autowired
    private ICommentService commentService;
    @Autowired
    private ICategoriesService categoriesService;
    @Autowired
    private CountryService countryService;


    @RequestMapping(value = "/loadUser")
    public String userHome(Model model) {

        String currentUser = userService.getCurrentUserName();
        List<Films> films = filmService.findAll();
        List<Categories> categories = categoriesService.findAll(); // Lấy danh sách thể loại
        List<Countries> countries = countryService.findAll();   // Lấy danh sách quốc gia
        model.addAttribute("films", films);
        model.addAttribute("user", currentUser);

        model.addAttribute("categories", categories); // Thêm danh sách thể loại vào mô hình
        model.addAttribute("countries", countries);   // Thêm danh sách quốc gia vào mô hình

        return "user/home";
    }

    @RequestMapping(value = "/profile")
    public String profileUser() {
        return "user/profile";
    }

    // Chị Viện Làm để điều hướng sang trang detail để bình luận nhé

    @GetMapping("/detailFilm/{id}")
    public String filmDetail(@PathVariable("id") Integer filmId, HttpSession session, Model model) {
        Films film = filmService.getFilmById(filmId);

        if (film == null) {
            return "redirect:/home";
        }

        Users currentUser = (Users) session.getAttribute("user");
        if (currentUser != null) {
            model.addAttribute("user", currentUser);
        }

        model.addAttribute("film", film); // Đảm bảo tên đối tượng là "film"
        Comments newComment = new Comments();
        model.addAttribute("comment", newComment);

        List<Comments> comments = commentService.getCommentsByFilmId(filmId);
        model.addAttribute("comments", comments);

        return "user/detail";
    }

    @PostMapping("/addComment")
    public String addComment(@ModelAttribute("comment") Comments comment, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // Xử lý lỗi
            return "user/detail";
        }

        // Lấy thông tin của Film và User từ request hoặc session
        Integer filmId = comment.getFilms().getFilmId();

        // Đảm bảo rằng filmId và userId không null
        if (filmId == null ) {
            model.addAttribute("error", "Film hoặc User không hợp lệ.");
            return "user/detail";
        }

        // Thiết lập lại Film và User trong comment nếu cần thiết
        Films film = filmService.getFilmById(filmId);

        if (film == null ) {
            model.addAttribute("error", "Film hoặc User không tồn tại.");
            return "user/detail";
        }

        comment.setFilms(film);

        // Thực hiện thêm bình luận
        boolean success = commentService.addComment(comment);
        if (!success) {
            model.addAttribute("error", "Có lỗi xảy ra khi thêm bình luận.");
            return "user/detail";
        }

        // Lấy lại danh sách bình luận mới nhất
        List<Comments> comments = commentService.getCommentsByFilmId(filmId);
        model.addAttribute("comments", comments);

        return "redirect:/detailFilm/" + filmId;
    }


}









