package ra.nhom1_watchingfilmonline.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class TestController {
    @GetMapping("")
    public String test() {
        return "test";
    }

    @PostMapping("")
    public String test() {
        return "test";
    }
}
