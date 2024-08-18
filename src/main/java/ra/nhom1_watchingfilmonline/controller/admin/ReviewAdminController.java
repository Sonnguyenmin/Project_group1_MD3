package ra.nhom1_watchingfilmonline.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ra.nhom1_watchingfilmonline.model.entity.Reviews;
import ra.nhom1_watchingfilmonline.service.IReviewService;

import java.util.List;

@Controller
public class ReviewAdminController {
    @Autowired
    private IReviewService reviewService;
    @RequestMapping(value = "/loadReview")
    public String loadReview(Model model) {
        List<Reviews> reviewsList = reviewService.getAllReviews();
        System.out.println("Danh sách danh mục: " + reviewsList); // Ghi log check
        model.addAttribute("reviews", reviewsList);
        return "admin/review/listReview";
    }



}
