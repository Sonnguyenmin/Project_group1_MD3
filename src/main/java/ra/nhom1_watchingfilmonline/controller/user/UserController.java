package ra.nhom1_watchingfilmonline.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import ra.nhom1_watchingfilmonline.service.IUserService;
import ra.nhom1_watchingfilmonline.service.UploadService;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ra.nhom1_watchingfilmonline.model.entity.Users;

import ra.nhom1_watchingfilmonline.service.*;

import ra.nhom1_watchingfilmonline.service.impl.BannerService;

import javax.servlet.http.HttpSession;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ra.nhom1_watchingfilmonline.model.entity.*;
import ra.nhom1_watchingfilmonline.service.impl.CountryService;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class UserController {
    @Autowired
    HttpSession session;
    @Autowired
    public IUserService userService;
    @Autowired
    public BannerService bannerService;
    @Autowired
    public UploadService uploadService;
    @Autowired
    private FilmService filmService;
    @Autowired
    private ICommentService commentService;
    @Autowired
    private ICategoriesService categoriesService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private IReviewService reviewService;
    @Autowired
    private IFavouriteService favouriteService;


    @RequestMapping(value = "/loadUser")

    public String userHome(Model model, HttpSession session) {
        Users userCurrent = (Users) session.getAttribute("userCurrent");
        model.addAttribute("userCurrent", userCurrent);
//        String currentUser = userService.getCurrentUserName();
        List<Films> films = filmService.getAllFilms();


        
        List<Favourite> favourites = favouriteService.getAllFavourites();


        model.addAttribute("bannerList",bannerService.findAll());




        List<Countries> countries = countryService.findAllCountries();   // Lấy danh sách quốc gia


//        model.addAttribute("films", films);
//        model.addAttribute("user", currentUser);
//        String currentUser = userService.getCurrentUserName();
//        List<Films> films = filmService.findAll();

        List<Categories> categories = categoriesService.findAll(); // Lấy danh sách thể loại


        model.addAttribute("categories", categories); // Thêm danh sách thể loại vào mô hình
        model.addAttribute("countries", countries);   // Thêm danh sách quốc gia vào mô hình

        model.addAttribute("films", films);
//        model.addAttribute("user", currentUser);

        model.addAttribute("categories", categories);
        model.addAttribute("countries", countries);
        model.addAttribute("favourites", favourites);


        return "user/home";
    }

    @RequestMapping(value = "/profile")
    public String profileUser() {
        return "user/profile";
    }


    @PostMapping("/changeProfile")
    public String updateProfile(
            @RequestParam("username") String userName,
            @RequestParam("email") String email,
            @RequestParam("fname") String fullName,
            @RequestParam("fileAvatar") MultipartFile fileAvatar, Model model
    ) {
        // Get the current user from the session

        Users currentUser = (Users) session.getAttribute("user");

        // Update the user's information
        currentUser.setUserName(userName);
        currentUser.setEmail(email);
        currentUser.setFullName(fullName);

        if (fileAvatar != null && !fileAvatar.isEmpty()) {
            String avatarUrl = uploadService.uploadFileToServer(fileAvatar);
            currentUser.setAvatar(avatarUrl);
        }
        // Save the updated user information
        userService.update(currentUser);

        // Update the session with the new user information
        session.setAttribute("user", currentUser);

        return "redirect:/profile";
    }

    @PostMapping("/changePassword")
    public String changePassword(
            @RequestParam("oldpass") String oldPass,
            @RequestParam("newpass") String newPass,
            Model model
    ) {
        // Get the current user from the session

        Users currentUser = (Users) session.getAttribute("user");

        // match old password -> change
        if (oldPass.equals(currentUser.getPassword())) {
            currentUser.setPassword(newPass);
        } else {
            model.addAttribute("error", "Sai mật khẩu cũ");
            return "user/profile";
        }

        // Save the updated user information
        userService.update(currentUser);

        // Update the session with the new user information
        session.setAttribute("user", currentUser);

        return "redirect:/profile";
    }

    @GetMapping("/deposit")
    public String openDeposit() {
        return "user/deposit";
    }

    @PostMapping("/deposit")
    public String deposit(@RequestParam("money") Integer money, Model model) {
        Users user = (Users) session.getAttribute("user");
        if (money < 0) {
            model.addAttribute("error", "money must be than 0");
            return "user/deposit";
        }
        userService.handleAddWallet(user, money, session);
        return "redirect:/profile";

    }

    @GetMapping("/byVip")
    public String openByVip() {
        return "user/byVip";
    }

    @PostMapping("/handleUpdateAcc")
    public String handleUpdateAcc(Model model) {
        Users user = (Users) session.getAttribute("user");
        boolean hasVipRole = user.getRoles().stream()
                .anyMatch(role -> "VIP".equals(role.getRoleName()));

        if (hasVipRole) {
            model.addAttribute("error", "Ban da la tai khoan Vip roi");
            return "user/byVip";
        }
        boolean check = userService.handleUpdateAcc(user);
        if (check) {
            return "redirect:/loadUser";
        } else {
            model.addAttribute("error", "you do not have enough money");
            return "user/byVip";
        }
    }

    // Chị Viện Làm để điều hướng sang trang detail để bình luận nhé

    @GetMapping("/detailFilm/{id}")
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
        Comments newComment = new Comments();
        model.addAttribute("comment", newComment);
        Reviews newreviews = new Reviews();
        model.addAttribute("reviews", newreviews);
        Favourite newFavourite = favouriteService.getFavouriteById(filmId);
        model.addAttribute("favourite", newFavourite);
//      khi ma submit len phan cho user xem
        List<Reviews> reviewsList = reviewService.getReviewByFilmId(filmId);
        model.addAttribute("reviewsList", reviewsList);
        List<Comments> comments = commentService.getCommentsByFilmId(filmId);
        model.addAttribute("comments", comments);
        List<Favourite> favourites = favouriteService.getFavouriteByFilmId(filmId);
        model.addAttribute("favourites ", favourites);

        return "user/detail";
    }

    @PostMapping("/addComment")
    public String addComment(@ModelAttribute("comment") Comments comment, Model model) {
        // Lấy thông tin của Film và User

        Integer filmId = comment.getFilms().getFilmId();

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









