package ra.nhom1_watchingfilmonline.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ra.nhom1_watchingfilmonline.service.FilmService;

@Controller
public class UFilmController {
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
}
