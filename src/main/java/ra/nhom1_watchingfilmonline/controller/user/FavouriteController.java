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
    public String loadFavourite(Model model) {
        model.addAttribute("favourite", favouriteService.getAllFavourites());
        List<Films> films = filmService.getAllFilms();
        List<Categories> categories = categoriesService.findAll(); // Lấy danh sách thể loại
        List<Countries> countries = countryService.findAll();// Lấy danh sách quốc gia
        List<Reviews> reviews = reviewService.getAllReviews();
        model.addAttribute("review", reviews);
        model.addAttribute("films", films);
        model.addAttribute("categories", categories);
        model.addAttribute("countries", countries);

        return "user/favourite";
    }

    @GetMapping("/initFavourite/{id}")
    public String filmDetail(@PathVariable("id") Integer filmId, HttpSession session, Model model) {
        Films film = filmService.findByIdWithCategories(filmId);

        if (film == null) {
            return "redirect:/home";
        }

        Users currentUser = (Users) session.getAttribute("user");
        if (currentUser != null) {
            model.addAttribute("user", currentUser);
        }

        model.addAttribute("film", film);

        Favourite newFavourite = favouriteService.getFavouriteById(filmId);
        model.addAttribute("favourite", newFavourite);

    //      khi ma submit len phan cho user xem

        List<Favourite> favourites = favouriteService.getFavouriteByFilmId(filmId);
        model.addAttribute("listFavors", favourites);
        return "user/favourite";
    }


    @PostMapping(value = "/addFavourite")
    public String addFavourite(@ModelAttribute("favourite") Favourite favourite, Model model) {

        // Lấy thông tin của Film và User
        Integer filmId = favourite.getFilms().getFilmId();

        // Đảm bảo rằng filmId và userId không null
        if (filmId == null) {
            model.addAttribute("error", "Film hoặc User không hợp lệ.");
            return "user/home";
        }

        // Thiết lập lại Film và User
        Films film = filmService.getFilmById(filmId);

        if (film == null) {
            model.addAttribute("error", "Film hoặc User không tồn tại.");
            return "user/home";
        }

        favourite.setFilms(film);

        boolean success = favouriteService.addFavourite(favourite);

        if (!success) {
            model.addAttribute("error", "Đã bi lỗi khi thêm review.");
            return "user/home";
        }

     //        tra ve list yeu thich cho nguoi xem
        List<Favourite> favourites = favouriteService.getFavouriteByFilmId(filmId);
        model.addAttribute("favourites", favourites);
        return  "redirect:/initFavourite/" + filmId;
    }

}
