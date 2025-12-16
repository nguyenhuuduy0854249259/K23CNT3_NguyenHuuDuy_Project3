package com.example.k23cnt3_nhd_lesson05.controller;

import com.example.k23cnt3_nhd_lesson05.entity.Info;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")   // 👈 Thêm path gốc
public class HomeController {

    @GetMapping
    public String index() {
        return "index"; // render index.html
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        List<Info> profile = new ArrayList<>();

        profile.add(new Info(
                "Nguyễn Hữu Duy",
                "master",
                "nhd@masterz.edu.vn",
                "https://masterz.edu.vn"
        ));

        model.addAttribute("nhdProfile", profile);
        return "profile"; // render profile.html
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("title", "Master::Trang chủ");
        return "index";
    }

    @GetMapping("/about")
    public String about(Model model) {
        return "about";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        return "contact";
    }

    @GetMapping("/services")
    public String services(Model model) {
        return "services";
    }
}
