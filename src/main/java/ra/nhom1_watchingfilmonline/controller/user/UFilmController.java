package ra.nhom1_watchingfilmonline.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ra.nhom1_watchingfilmonline.service.FilmService;

@Controller
public class UFilmController {
    @Autowired
    FilmService filmService;

    @GetMapping("/phimbo")
    public String phimbo(Model model,
                         @RequestParam(value = "error_fa", required = false) String errorFa){
        model.addAttribute("phimbo",filmService.findAllPhimBo());
        if (errorFa != null) {
            model.addAttribute("error_fa", errorFa);
        }
        return "user/phimbo";
    }

    @GetMapping("/phimle")
    public String phimle(Model model,
                         @RequestParam(value = "error_fa", required = false) String errorFa){

        model.addAttribute("phimle",filmService.findAllPhimLe());
        if (errorFa != null) {
            model.addAttribute("error_fa", errorFa);
        }
        return "user/phimle";
    }

}
