package ra.nhom1_watchingfilmonline.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ra.nhom1_watchingfilmonline.model.dto.request.FilmRequest;
import ra.nhom1_watchingfilmonline.model.entity.Categories;
import ra.nhom1_watchingfilmonline.model.entity.Films;
import ra.nhom1_watchingfilmonline.service.FilmService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UFilmController {
    @Autowired
    private HttpSession session;

    @Autowired
    FilmService filmService;

    @GetMapping("/phimbo")
    public String phimbo(Model model){
        model.addAttribute("phimbo",filmService.findAllPhimBo());
        return "user/phimbo";
    }

    @GetMapping("/phimle")
    public String phimle(Model model){
        model.addAttribute("phimle",filmService.findAllPhimLe());
        return "user/phimle";
    }

    @GetMapping("/shopFilm")
    public String shopFilm(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "12") Integer size,
            @RequestParam(name = "search", defaultValue = "") String search,
            @ModelAttribute("filmRequest") FilmRequest films,
            Model model
    ){
        //set session
        session.setAttribute("active_film", "films");
        //set list film pagination
        model.addAttribute("films", filmService.findAllUserFilm(page, size, search));
        model.addAttribute("page", page);
        model.addAttribute("search", search);
        model.addAttribute("size", size);
        //totalPages
        Double totalPages = Math.ceil((double) filmService.totalAllFilm(search) / size);
        model.addAttribute("totalPages", totalPages);
        return "user/shopFilm";
    }


    @GetMapping("/shopFilm/sortUFilmList")
    public String sortByName(Model model, @RequestParam(value = "sort", defaultValue = "asc") String sort,
                             @RequestParam(name = "page", defaultValue = "0") Integer page,
                             @RequestParam(name = "size", defaultValue = "12") Integer size,
                             @RequestParam(name = "search", defaultValue = "") String search
    ) {
        List<Films> films;
        if ("desc".equalsIgnoreCase(sort)) {
            films = filmService.findAllByOrderByUFilmNameDesc(page, size);
        } else {
            films = filmService.findAllByOrderByUFilmNameAsc(page, size);
        }
        //totalPages
        Double totalPages = Math.ceil((double) filmService.totalAllFilm(search) / size);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("films", films);
        model.addAttribute("search", search);
        model.addAttribute("sort", sort);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        return "user/shopFilm";
    }
}
