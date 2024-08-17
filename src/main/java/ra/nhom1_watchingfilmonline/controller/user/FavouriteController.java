package ra.nhom1_watchingfilmonline.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ra.nhom1_watchingfilmonline.model.entity.*;
import ra.nhom1_watchingfilmonline.service.ICategoriesService;
import ra.nhom1_watchingfilmonline.service.IFavouriteService;
import ra.nhom1_watchingfilmonline.service.IReviewService;
import ra.nhom1_watchingfilmonline.service.IUserService;
import ra.nhom1_watchingfilmonline.service.impl.CountryService;
import ra.nhom1_watchingfilmonline.service.impl.FilmServiceImpl;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;


@Controller
public class FavouriteController {
    @Autowired
    private IFavouriteService favouriteService;

    @Autowired
    private FilmServiceImpl filmService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ICategoriesService categoriesService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private IReviewService reviewService;

    @RequestMapping(value = "/favourite")
    public String loadFavourite(Model model, HttpSession session) {
        Users currentUser = (Users) session.getAttribute("user");
        List<Favourite> favourites = favouriteService.findByUser_UserId(currentUser.getUserId());
        model.addAttribute("favouriteList", favourites);
        return "user/favourite";  // Trang hiển thị danh sách yêu thích
    }

    @GetMapping("/initFavourite/{id}")
    public String filmDetail(@PathVariable("id") Integer filmId,
                             HttpSession session, Model model) {
        Films film = filmService.findByIdWithCategories(filmId);

        if (film == null) {
            return "redirect:/home";
        }

        Users currentUser = (Users) session.getAttribute("user");
        if (currentUser != null) {
            model.addAttribute("user", currentUser);
        }

        model.addAttribute("film", film);

    //      khi ma submit len phan cho user xem

        List<Favourite> favourites = favouriteService.getFavouriteByFilmId(filmId);
        model.addAttribute("favouriteList ", favourites);
        return "user/favourite";
    }


    @PostMapping(value = "/addFavourite")
    public String addFavourite(@ModelAttribute("favourite") Favourite favourite,HttpSession session, Model model) {

        Integer filmId = favourite.getFilms().getFilmId();
        Users currentUser = (Users) session.getAttribute("user");
        String currentPage = (String) session.getAttribute("currentPage");

        if (filmId == null || currentUser == null) {
            model.addAttribute("error", "Film hoặc User không hợp lệ.");
            return "user/home";
        }

        // Kiểm tra xem yêu thích đã tồn tại chưa
        if (favouriteService.isFavouriteExists(filmId, currentUser.getUserId())) {
            List<Films> films = filmService.getAllFilms();
            model.addAttribute("films", films);
            model.addAttribute("error_fa", "Bạn đã yêu thích bộ phim này rồi.");
            return redirectBasedOnPage(currentPage);
        }

        Films film = filmService.getFilmById(filmId);

        if (film == null) {
            model.addAttribute("error", "Film không tồn tại.");
            return "user/home";
        }

        favourite.setFilms(film);
        favourite.setUsers(currentUser);

        boolean success = favouriteService.addFavourite(favourite);

        if (!success) {
            model.addAttribute("error", "Đã có lỗi khi thêm yêu thích.");
            return "user/home";
        }

        List<Favourite> favourites = favouriteService.getFavouriteByFilmId(filmId);
        model.addAttribute("favouriteList ", favourites);

        return "redirect:/favourite";
    }

// phuong thuc tra ve duong dan
    private String redirectBasedOnPage(String currentPage) {
        if (currentPage == null || currentPage.isEmpty()) {
            return "redirect:/home";
        }
        return "redirect:" + currentPage;
    }
}
