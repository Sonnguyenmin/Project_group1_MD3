package ra.nhom1_watchingfilmonline.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ra.nhom1_watchingfilmonline.model.entity.Comments;
import ra.nhom1_watchingfilmonline.model.entity.Films;
import ra.nhom1_watchingfilmonline.model.entity.Reviews;
import ra.nhom1_watchingfilmonline.model.entity.Users;
import ra.nhom1_watchingfilmonline.service.FilmService;
import ra.nhom1_watchingfilmonline.service.IReviewService;
import ra.nhom1_watchingfilmonline.service.IUserService;
import ra.nhom1_watchingfilmonline.service.impl.FilmServiceImpl;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ReviewController {
 @Autowired
    private IReviewService reviewService;

 @Autowired
    private FilmServiceImpl filmService;

 @Autowired
 private IUserService userService;


 @RequestMapping(value = "/review")
    public String loadReview(Model model) {
     model.addAttribute("reviews", reviewService.getAllReviews());
     return "user/home";
 }

    @GetMapping("/detail/{id}")
    public String filmDetail(@PathVariable("id") Integer filmId, HttpSession session, Model model) {
        Films film = filmService.findByIdWithCategories(filmId);

        if (film == null) {
            return "redirect:/home";
        }

        Users currentUser = (Users) session.getAttribute("user");
        if (currentUser != null) {
            model.addAttribute("user", currentUser);
        }
//        lay tat ca review;
        model.addAttribute("film", film);
        Reviews newreviews = new Reviews();
        model.addAttribute("reviews", newreviews);
//      khi ma submit len phan cho user xem
        List<Reviews> reviewsList = reviewService.getReviewByFilmId(filmId);
        model.addAttribute("reviewsList", reviewsList);

        return "user/detail";
    }
    @GetMapping(value = "addReview")
    public String addReview(@ModelAttribute("reviews") Reviews reviews, Model model) {

        // Lấy thông tin của Film và User
        Integer filmId = reviews.getFilms().getFilmId();

        // Đảm bảo rằng filmId và userId không null
        if (filmId == null) {
            model.addAttribute("error", "Film hoặc User không hợp lệ.");
            return "user/detail";
        }

        // Thiết lập lại Film và User
        Films film = filmService.getFilmById(filmId);

        if (film == null) {
            model.addAttribute("error", "Film hoặc User không tồn tại.");
            return "user/detail";
        }

        reviews.setFilms(film);

        boolean success = reviewService.saveReview(reviews);

        if (!success) {
            model.addAttribute("error", "Đã bi lỗi khi thêm review.");
            return "user/detail";
        }
        List<Reviews> reviewsList = reviewService.getReviewByFilmId(filmId);
        model.addAttribute("reviewsList", reviewsList);
        return "redirect:/detail/" + filmId;
    }

}
