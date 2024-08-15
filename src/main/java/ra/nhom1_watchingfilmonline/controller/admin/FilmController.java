package ra.nhom1_watchingfilmonline.controller.admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ra.nhom1_watchingfilmonline.model.dto.request.FilmRequest;
import ra.nhom1_watchingfilmonline.model.entity.Categories;
import ra.nhom1_watchingfilmonline.model.entity.Films;
import ra.nhom1_watchingfilmonline.model.entity.Users;
import ra.nhom1_watchingfilmonline.repository.impl.CategoriesRepositoryImpl;
import ra.nhom1_watchingfilmonline.repository.impl.CountryDao;
import ra.nhom1_watchingfilmonline.service.impl.FilmServiceImpl;


import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/film")
public class FilmController {

    @Autowired
    private FilmServiceImpl filmService;

    @Autowired
    private CategoriesRepositoryImpl categoriesRepository;

    @Autowired
    private CountryDao countryDao;

    @GetMapping("")
    public String formFilm(Model model) {
        model.addAttribute("films", filmService.findAll());
        return "admin/films/listFilm";
    }

    @GetMapping("/add")
    public String formAddFilm(Model model) {
        model.addAttribute("countries", countryDao.findAll());
        model.addAttribute("categories", categoriesRepository.findAll());
        FilmRequest filmRequest = new FilmRequest();
        filmRequest.setStatus(1);
        model.addAttribute("filmRequest", filmRequest);
        return "admin/films/addFilm";
    }

    @PostMapping("/add")
    public String addFilm(
            @Valid @ModelAttribute("films") FilmRequest films,
            BindingResult result,
            Model model
    )
    {
        if (result.hasErrors()) {
//            model.addAttribute("countries", countryDao.findAll());
//            model.addAttribute("categories", categoriesRepository.findAll());
//            model.addAttribute("filmRequest", films);
            return "admin/films/addFilm";
        }
        try {
            Films existingFilm = filmService.findFilmByName(films.getFilmName());
            if (existingFilm != null) {
                model.addAttribute("errorMessage", "Phim với tên '" + films.getFilmName() + "' đã tồn tại.");
                return "admin/films/addFilm";
            }

            filmService.saveFilm(films);
            return "redirect:/film";
        } catch (Exception ex) {
            ex.printStackTrace();
            model.addAttribute("errorMessage", "Lỗi khi lưu danh mục: " + ex.getMessage());
            return "admin/films/addFilm";
        }
    }

    @GetMapping("/edit/{id}")
    public String formEditFilm(@Valid @PathVariable("id") Integer id, Model model) {
        model.addAttribute("filmRequest", filmService.getFilmById(id));
        return "admin/films/editFilm";
    }

    @GetMapping("/delete/{id}")
    public String formDeleteFilm(@PathVariable Integer id, Model model) {
        try {
            Films films = filmService.getFilmById(id);
            if (films == null) {
                model.addAttribute("error", "Phim này không tồn tại");
                return "redirect:/film";
            } else {
                filmService.deleteFilm(id);
                return "redirect:/film";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            model.addAttribute("errorMessage", "Lỗi khi xóa phim: " + ex.getMessage());
            return "redirect:/film";
        }
    }

    @GetMapping("/detail/{id}")
    public String formDetailFilm(@PathVariable Integer id, Model model) {
        try {
            Films films = filmService.getFilmById(id);
            if (films == null) {
                model.addAttribute("errorMessage", "Phim không tồn tại.");
                return "redirect:/film";
            }
            model.addAttribute("films", films);

            List<Categories> categories = categoriesRepository.findAll();
            model.addAttribute("categories", categories);
            return "admin/films/detailFilm";

        } catch (Exception ex) {
            model.addAttribute("errorMessage", "Lỗi khi lấy thông tin phim: " + ex.getMessage());
            return "admin/films/detailFilm";
        }
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String showHomePage(@RequestParam(value = "filmId", required = false) Integer filmId, Model model) {
        List<Films> films = filmService.findAll(); // Lấy danh sách phim

        model.addAttribute("films", films);

        if (filmId != null) {
            Films selectedFilm = filmService.getFilmById(filmId); // Tìm phim theo id
            if (selectedFilm != null) {
                model.addAttribute("film", selectedFilm);
            } else {
                model.addAttribute("error", "Phim không tồn tại.");
            }
        }
        return "user/home";
    }

    @RequestMapping(value = "/detailFilm/{id}", method = RequestMethod.GET)
    public String detailFilm(@PathVariable("id") Integer filmId, HttpSession session, Model model) {
        // Lấy thông tin phim theo id
        Films film = filmService.getFilmById(filmId);

        if (film != null) {
            model.addAttribute("film", film);
            // Lấy thông tin người dùng hiện tại
            Users user = (Users) session.getAttribute("user");
            model.addAttribute("user", user);
            return "user/detail"; // Trang chi tiết phim
        } else {
            // Xử lý nếu phim không tồn tại
            return "redirect:/home"; // Quay lại trang chính nếu không tìm thấy phim
        }
    }


}
