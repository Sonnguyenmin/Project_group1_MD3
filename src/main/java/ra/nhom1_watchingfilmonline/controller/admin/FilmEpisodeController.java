package ra.nhom1_watchingfilmonline.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ra.nhom1_watchingfilmonline.model.dto.request.FilmEpisodeRequest;
import ra.nhom1_watchingfilmonline.model.entity.FilmEpisode;
import ra.nhom1_watchingfilmonline.service.FilmEpisodeService;
import ra.nhom1_watchingfilmonline.service.impl.FilmServiceImpl;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/filmEpisode")
public class FilmEpisodeController {
    @Autowired
    private FilmServiceImpl filmService;

    @Autowired
    private FilmEpisodeService filmEpisodeService;


    @GetMapping("")
    public String formFilmEpisode(Model model) {
        model.addAttribute("filmEpisodes",filmEpisodeService.findAll());
        return "admin/filmEpisode/listFilmEpisode";
    }

    @GetMapping("/add")
    public String formAddFilmEpisode(Model model, @RequestParam(name = "page", defaultValue = "0") Integer page,
                                     @RequestParam(name = "size", defaultValue = "5") Integer size,
                                     @RequestParam(name = "search", defaultValue = "") String search) {
        model.addAttribute("films",filmService.findAll(page, size, search));
        FilmEpisodeRequest filmEpisodes = new FilmEpisodeRequest();
        model.addAttribute("filmEpisodes", filmEpisodes);
        return "admin/filmEpisode/addFilmEpisode";
    }

    @PostMapping("/add")
    public String addFilmEpisode(
            @Valid @ModelAttribute("filmEpisodes") FilmEpisodeRequest filmEpisodes,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            return "admin/filmEpisode/addFilmEpisode";
        }
        try {
            filmEpisodeService.save(filmEpisodes);
            return "redirect:/filmEpisode";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "admin/filmEpisode/addFilmEpisode";

        }
    }


    @GetMapping("/delete/{id}")
    public String formDeleteFilmEpisode(@PathVariable Integer id, Model model) {
        try {
            FilmEpisode filmEpisode = filmEpisodeService.findById(id);
            if (filmEpisode == null) {
                model.addAttribute("error", "Tập phim này không tồn tại");
                return "redirect:/filmEpisode";
            } else {
                filmEpisodeService.delete(id);
                return "redirect:/filmEpisode";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            model.addAttribute("errorMessage", "Lỗi khi xóa tập phim: " + ex.getMessage());
            return "redirect:/film";
        }
    }

}
