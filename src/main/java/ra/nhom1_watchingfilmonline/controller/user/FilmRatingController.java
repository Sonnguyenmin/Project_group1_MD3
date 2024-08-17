package ra.nhom1_watchingfilmonline.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ra.nhom1_watchingfilmonline.model.entity.Films;
import ra.nhom1_watchingfilmonline.service.FilmService;

import java.util.List;

@Controller
public class FilmRatingController {
    @Autowired
    private FilmService filmService;
// phim de xuat
    @GetMapping("/recommendedFilm")
    public String getTopRecommendedFilms(Model model) {
        // Lấy danh sách 5 phim có điểm trung bình cao nhất
        List<Films> topFilms = filmService.getTop5RecommendedFilms();
        model.addAttribute("topFilms", topFilms);
        return "/user/home";
    }

}
