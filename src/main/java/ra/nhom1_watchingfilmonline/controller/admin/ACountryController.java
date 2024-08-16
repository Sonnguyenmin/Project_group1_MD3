package ra.nhom1_watchingfilmonline.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ra.nhom1_watchingfilmonline.model.entity.Countries;
import ra.nhom1_watchingfilmonline.model.entity.Films;
import ra.nhom1_watchingfilmonline.service.impl.CountryService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/country")
public class ACountryController {
    @Autowired
    private HttpSession session;
    @Autowired
    private CountryService countryService;

    @GetMapping("")
    public String countries(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @RequestParam(name = "search", defaultValue = "") String search,
            Model model){
        //set session
        session.setAttribute("active_country", "countries");
        //set list film pagination
        model.addAttribute("countryList", countryService.findAll(page, size, search));
        model.addAttribute("page", page);
        model.addAttribute("search", search);
        model.addAttribute("size", size);
        //totalPages
        Double totalPages = Math.ceil((double) countryService.totalAllCountry(search) / size);
        model.addAttribute("totalPages", totalPages);
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
        return "redirect:/country";
    }

    @GetMapping("/edit/{id}")
    public String openEdit(@PathVariable Integer id, Model model){
        model.addAttribute("countries",countryService.findById(id));
        return "admin/country/editCountry";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Countries countries){
        countryService.save(countries);
        return "redirect:/country";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        countryService.delete(id);
        return "redirect:/country";
    }


    @GetMapping("/sortCountryList")
    public String sortByName(Model model, @RequestParam(value = "sort", defaultValue = "asc") String sort,
                             @RequestParam(name = "page", defaultValue = "0") Integer page,
                             @RequestParam(name = "size", defaultValue = "5") Integer size,
                             @RequestParam(name = "search", defaultValue = "") String search
    ) {
        List<Countries> countries;
        if ("desc".equalsIgnoreCase(sort)) {
            countries = countryService.findAllByOrderByCountryDesc(page, size);
        } else {
            countries = countryService.findAllByOrderByCountryAsc(page, size);
        }
        //totalPages
        Double totalPages = Math.ceil((double) countryService.totalAllCountry(search) / size);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("countryList", countries);
        model.addAttribute("search", search);
        model.addAttribute("sort", sort);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        return "admin/country/listCountry";

    }
}
