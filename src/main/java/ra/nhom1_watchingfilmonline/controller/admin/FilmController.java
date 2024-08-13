package ra.nhom1_watchingfilmonline.controller.admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ra.nhom1_watchingfilmonline.service.impl.FilmServiceImpl;

@Controller
@RequestMapping("/admin")
public class FilmController {

    @Autowired
    private FilmServiceImpl filmService;

    @GetMapping("/film")
    public String formFilm(Model model) {
        model.addAttribute("filmList", )
        return "admin/films/listFilm";
    }

    @GetMapping("/add_film")
    public String formAddFilm(Model model) {
        return "admin/films/addFilm";
    }

    @GetMapping("/edit_film")
    public String formEditFilm(Model model) {
        return "admin/films/editFilm";
    }

    @GetMapping("/delete_film")
    public String formDeleteFilm(Model model) {
        return "admin/films/listFilm";
    }

    @GetMapping("/detail_film")
    public String formDetailFilm(Model model) {
        return "admin/films/detailFilm";
    }

}
