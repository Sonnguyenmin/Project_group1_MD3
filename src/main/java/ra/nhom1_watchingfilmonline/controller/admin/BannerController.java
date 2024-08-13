package ra.nhom1_watchingfilmonline.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ra.nhom1_watchingfilmonline.model.dto.request.BannerRequest;
import ra.nhom1_watchingfilmonline.service.impl.BannerService;

@Controller
@RequestMapping("/admin/banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;

    @GetMapping("")
    public String banners(Model model){
        model.addAttribute("bannerList",bannerService.findAll());
        return "admin/banner/listBanner";
    }

    @GetMapping("/add")
    public String openAdd(Model model){
        model.addAttribute("bannerRequest",new BannerRequest());
        return "admin/banner/addBanner";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute BannerRequest bannerRequest){
        bannerService.save(bannerRequest);
        return "redirect:/admin/banner";
    }

    @GetMapping("/edit/{id}")
    public String openEdit(@PathVariable Integer id,Model model){
        model.addAttribute("bannerRequest",bannerService.findById(id));
        return "admin/banner/editBanner";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute BannerRequest bannerRequest){
        bannerService.save(bannerRequest);
        return "redirect:/admin/banner";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id,Model model){
        bannerService.delete(id);
        return "admin/banner/editBanner";
    }

}
