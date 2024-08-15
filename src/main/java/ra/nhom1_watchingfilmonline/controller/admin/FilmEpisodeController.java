package ra.nhom1_watchingfilmonline.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ra.nhom1_watchingfilmonline.model.dto.request.FilmEpisodeRequest;
import ra.nhom1_watchingfilmonline.model.dto.request.FilmRequest;
import ra.nhom1_watchingfilmonline.model.entity.FilmEpisode;
import ra.nhom1_watchingfilmonline.model.entity.Films;
import ra.nhom1_watchingfilmonline.service.FilmEpisodeService;
import ra.nhom1_watchingfilmonline.service.impl.FilmServiceImpl;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/filmEpisode")
public class FilmEpisodeController {
    @Autowired
    private HttpSession session;

    @Autowired
    private FilmServiceImpl filmService;

    @Autowired
    private FilmEpisodeService filmEpisodeService;


    @GetMapping("")
    public String formFilmEpisode(Model model) {
        model.addAttribute("filmEpisodeList",filmEpisodeService.findAll());
        return "admin/filmEpisode/listFilmEpisode";
    }

    @GetMapping("/add")
    public String formAddFilm(Model model, @RequestParam(name = "page", defaultValue = "0") Integer page,
                              @RequestParam(name = "size", defaultValue = "5") Integer size,
                              @RequestParam(name = "search", defaultValue = "") String search) {
        model.addAttribute("films", filmService.findAll(page, size, search));
        model.addAttribute("page", page);
        model.addAttribute("search", search);
        model.addAttribute("size", size);
        FilmEpisodeRequest filmEpisodeRequest = new FilmEpisodeRequest();
        model.addAttribute("filmEpisodeRequest", filmEpisodeRequest);
        return "admin/filmEpisode/addFilmEpisode";
    }

    @PostMapping("/add")
    public String addFilm(
            @Valid @ModelAttribute("filmEpisode") FilmEpisodeRequest filmEpisode,
            BindingResult result,
            Model model
    )
    {
        if (result.hasErrors()) {
            return "admin/filmEpisode/addFilmEpisode";
        }
        try {
            filmEpisodeService.save(filmEpisode);
            return "redirect:/filmEpisode";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "admin/filmEpisode/addFilmEpisode";

        }
    }

}
