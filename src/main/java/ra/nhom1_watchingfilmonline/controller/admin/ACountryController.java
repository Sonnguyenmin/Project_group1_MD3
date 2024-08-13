package ra.nhom1_watchingfilmonline.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ra.nhom1_watchingfilmonline.model.entity.Countries;
import ra.nhom1_watchingfilmonline.service.impl.CountryService;

@Controller
@RequestMapping("/admin/country")
public class ACountryController {
    @Autowired
    private CountryService countryService;

    @GetMapping("")
    public String countries(Model model){
        model.addAttribute("countryList",countryService.findAll());
        return "admin/country/listCountry";
    }

    @GetMapping("/add")
    public String openAdd(Model model){
        model.addAttribute("countries",new Countries());
        return "admin/country/addCountry";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Countries countries){
        countryService.save(countries);
        return "redirect:/admin/country";
    }

    @GetMapping("/edit/{id}")
    public String openEdit(@PathVariable Integer id, Model model){
        model.addAttribute("countries",countryService.findById(id));
        return "admin/country/editCountry";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Countries countries){
        countryService.save(countries);
        return "redirect:/admin/country";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        countryService.delete(id);
        return "redirect:/admin/country";
    }
}
