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
import ra.nhom1_watchingfilmonline.service.impl.FilmServiceImpl;

import javax.validation.Valid;

@Controller
@RequestMapping("/film")
public class FilmController {

    @Autowired
    private FilmServiceImpl filmService;

    @Autowired
    private CategoriesRepositoryImpl categoriesRepository;


    @GetMapping("")
    public String formFilm(Model model, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "5") Integer size) {
//        Integer pageSize = Math.max(size,1);
//        int currentPage  = Math.max(page,0);
//        Integer totalFilms = filmService.getTotalFilms();
//        int totalPages = (int) Math.ceil((double) totalFilms / pageSize);
//        model.addAttribute("totalPages", totalPages);
//        model.addAttribute("size", size);
//        model.addAttribute("currentPage", currentPage);
        model.addAttribute("films", filmService.findAll());
        return "admin/films/listFilm";
    }

    @GetMapping("/add")
    public String formAddFilm(Model model) {
        model.addAttribute("categories", categoriesRepository.findAll());
        FilmRequest filmRequest = new FilmRequest();
        filmRequest.setStatus(1);
        model.addAttribute("filmRequest", filmRequest);
        return "admin/films/addFilm";
    }

    @PostMapping("/add")
    public String addFilm(@Valid @ModelAttribute("films") Films films,
                          BindingResult result , Model model, FilmRequest filmRequest) {
        if(result.hasErrors()) {
            return "admin/films/addFilm";
        }
        try {
            Films existingFilm = filmService.findFilmByName(films.getFilmName());
            if (existingFilm != null) {
                model.addAttribute("errorMessage", "Phim với tên '" + films.getFilmName() + "' đã tồn tại.");
                return "admin/films/addFilm";
            }

            filmService.saveFilm(filmRequest);
            return "redirect:/film";
        } catch (Exception ex) {
            ex.printStackTrace();
            model.addAttribute("errorMessage", "Lỗi khi lưu danh mục: " + ex.getMessage());
            return "admin/films/addFilm";
        }

    }

    @GetMapping("/edit")
    public String formEditFilm(Model model) {
        return "admin/films/editFilm";
    }

    @GetMapping("/delete/{id}")
    public String formDeleteFilm(@PathVariable Integer id, Model model) {
        try {
            Films films = filmService.getFilmById(id);
            if (films == null) {
                model.addAttribute("error", "Phim này không tồn tại");
                return "redirect:/film";
            } else  {
                filmService.deleteFilm(id);
                return "redirect:/film";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            model.addAttribute("errorMessage", "Lỗi khi xóa phim: " + ex.getMessage());
            return "redirect:/film";
        }
    }

    @GetMapping("/detail")
    public String formDetailFilm(Model model) {
        return "admin/films/detailFilm";
    }

}
