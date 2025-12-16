package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.Controller;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service.LienHeService;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.LienHe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/lienhe")
public class LienHeController {

    @Autowired
    private LienHeService service;

    @GetMapping
    public String form(Model model) {
        model.addAttribute("lh", new LienHe());
        return "lienhe/form";
    }

    @PostMapping
    public String submit(@ModelAttribute LienHe lh) {
        service.save(lh);
        return "redirect:/lienhe?success";
    }
}

