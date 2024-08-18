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

 @GetMapping(value = "addReviews")
    public String addReview(@ModelAttribute("reviews") Reviews reviews, Model model,HttpSession session) {

        // Lấy thông tin của Film và User
        Integer filmId = reviews.getFilms().getFilmId();
        Integer userId = ((Users) session.getAttribute("user")).getUserId();
        String currentPage = (String) session.getAttribute("currentPage");

        // Đảm bảo rằng filmId và userId không null
        if (filmId == null || userId == null) {
            model.addAttribute("error", "Film hoặc User không hợp lệ.");
            return "user/detail";
        }

        // Thiết lập lại Film và User
        Films film = filmService.getFilmById(filmId);

        if (film == null) {
            model.addAttribute("error", "Film hoặc User không tồn tại.");
            return "user/detail";
        }

        // Lấy thông tin của người dùng
       Users user = userService.findUserById(userId);
        if (user == null) {
         model.addAttribute("error", "User không tồn tại.");
         return "user/detail";
     }
        reviews.setFilms(film);
        reviews.setUsers(userService.findUserById(userId));

     // Kiểm tra xem người dùng đã đánh giá bộ phim này chưa
        Reviews existingReview = reviewService.getReviewByFilmAndUser(filmId, userId);

        if (existingReview != null) {
            model.addAttribute("reviewsList", reviewService.getAllReviews());
         // Nếu đánh giá đã tồn tại, hiển thị thông báo lỗi
            session.setAttribute("errorReview", "Bạn đã đánh giá bộ phim này rồi.");
            System.out.println("Redirecting with errorReview: " + session.getAttribute("errorReview"));
         return redirectBasedOnPage(currentPage);
        }
        // Thêm đánh giá mới
        boolean success = reviewService.saveReview(reviews);
        if (!success) {
            model.addAttribute("error", "Đã bi lỗi khi thêm review.");
            return "user/detail";
        }
        List<Reviews> reviewsList = reviewService.getReviewByFilmId(filmId);
        model.addAttribute("reviewsList", reviewsList);
        return "redirect:/detailFilm/" + filmId;
    }

    // phuong thuc tra ve duong dan
    private String redirectBasedOnPage(String currentPage) {
        if (currentPage == null || currentPage.isEmpty()) {
            return "redirect:/home";
        }
        return "redirect:" + currentPage;
    }

}
