package ra.nhom1_watchingfilmonline.controller.admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ra.nhom1_watchingfilmonline.model.dto.request.FilmRequest;
import ra.nhom1_watchingfilmonline.model.entity.Categories;
import ra.nhom1_watchingfilmonline.model.entity.Films;
import ra.nhom1_watchingfilmonline.repository.impl.CategoriesRepositoryImpl;
import ra.nhom1_watchingfilmonline.repository.impl.CountryDao;
import ra.nhom1_watchingfilmonline.service.impl.FilmServiceImpl;


import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import javax.validation.Valid;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/film")
public class FilmController {
    @Autowired
    private HttpSession session;

    @Autowired
    private FilmServiceImpl filmService;

    @Autowired
    private CategoriesRepositoryImpl categoriesRepository;

    @Autowired
    private CountryDao countryDao;


    @GetMapping("")
    public String formFilm( @RequestParam(name = "page", defaultValue = "0") Integer page,
                            @RequestParam(name = "size", defaultValue = "5") Integer size,
                            @RequestParam(name = "search", defaultValue = "") String search,
                            Model model) {

        //set session
        session.setAttribute("active_film", "films");
        //set list film pagination
        model.addAttribute("films", filmService.findAll(page, size, search));
        model.addAttribute("page", page);
        model.addAttribute("search", search);
        model.addAttribute("size", size);
        //totalPages
        Double totalPages = Math.ceil((double) filmService.totalAllFilm(search) / size);
        model.addAttribute("totalPages", totalPages);
        return "admin/films/listFilm";
    }

    @GetMapping("/add")
    public String formAddFilm(Model model) {
        model.addAttribute("countries", countryDao.findAllCountries());
        model.addAttribute("categories", categoriesRepository.findAll());
        FilmRequest filmRequest = new FilmRequest();
        filmRequest.setStatus(1);
        model.addAttribute("filmRequest", filmRequest);
        return "admin/films/addFilm";
    }

    @PostMapping("/add")
    public String addFilm(
            @Valid @ModelAttribute("filmRequest") FilmRequest films,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("countries", countryDao.findAllCountries());
            model.addAttribute("categories", categoriesRepository.findAll());

            model.addAttribute("filmRequest", films);
            return "admin/films/addFilm";
        }
        try {
            Films existingFilm = filmService.findFilmByName(films.getFilmName());
            if (existingFilm != null) {
                model.addAttribute("countries", countryDao.findAllCountries());
                model.addAttribute("categories", categoriesRepository.findAll());
                FilmRequest filmRequest = new FilmRequest();
                filmRequest.setStatus(1);
                model.addAttribute("filmRequest", filmRequest);
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
    public String formEditFilm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("filmRequest", filmService.getFilmById(id));
        model.addAttribute("countries", countryDao.findAllCountries());
        model.addAttribute("categories", categoriesRepository.findAll());
        return "admin/films/editFilm";
    }


    @PostMapping("/edit")
    public String editFilm(@Valid @ModelAttribute("filmRequest") FilmRequest films, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("filmRequest",films);
            //lấy danh sách các ID của thể loại phim mà phim cụ thể thuộc về và gán danh sách này vào mô hình để sử dụng trong view.
            model.addAttribute("selectedValues",filmService.getFilmById(films.getFilmId()).getCategories().stream().map(Categories::getCategoryId).collect(Collectors.toList()));
            model.addAttribute("countries", countryDao.findAllCountries());
            model.addAttribute("categories", categoriesRepository.findAll());
            return "admin/films/editFilm";
        }
        try {

            Films existingFilm = filmService.findFilmByName(films.getFilmName());
            if (existingFilm != null) {
                model.addAttribute("filmRequest",films);
                model.addAttribute("selectedValues",filmService.getFilmById(films.getFilmId()).getCategories().stream().map(Categories::getCategoryId).collect(Collectors.toList()));
                model.addAttribute("countries", countryDao.findAllCountries());
                model.addAttribute("categories", categoriesRepository.findAll());
                model.addAttribute("errorMessage", "Phim với tên '" + films.getFilmName() + "' đã tồn tại.");
                return "admin/films/editFilm";
            }


            filmService.saveFilm(films);
            return "redirect:/film";
        } catch (Exception ex) {
            ex.printStackTrace();
            model.addAttribute("errorMessage", "Lỗi khi lưu danh mục: " + ex.getMessage());
            return "admin/films/editFilm";
        }
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
            model.addAttribute("selectedValues",filmService.getFilmById(id).getCategories().stream().map(Categories::getCategoryId).collect(Collectors.toList()));
            return "admin/films/detailFilm";

        } catch (Exception ex) {
            model.addAttribute("errorMessage", "Lỗi khi lấy thông tin phim: " + ex.getMessage());
            return "admin/films/detailFilm";
        }
    }



    @GetMapping("/sortFilmList")
    public String sortByName(Model model, @RequestParam(value = "sort", defaultValue = "asc") String sort,
                             @RequestParam(name = "page", defaultValue = "0") Integer page,
                             @RequestParam(name = "size", defaultValue = "5") Integer size,
                             @RequestParam(name = "search", defaultValue = "") String search
                             ) {
        List<Films> films;
        if ("desc".equalsIgnoreCase(sort)) {
            films = filmService.findAllByOrderByFilmDesc(page, size);
        } else {
            films = filmService.findAllByOrderByFilmAsc(page, size);
        }
        //totalPages
        Double totalPages = Math.ceil((double) filmService.totalAllFilm(search) / size);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("films", films);
        model.addAttribute("search", search);
        model.addAttribute("sort", sort);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        return "admin/films/listFilm";
    }

}
