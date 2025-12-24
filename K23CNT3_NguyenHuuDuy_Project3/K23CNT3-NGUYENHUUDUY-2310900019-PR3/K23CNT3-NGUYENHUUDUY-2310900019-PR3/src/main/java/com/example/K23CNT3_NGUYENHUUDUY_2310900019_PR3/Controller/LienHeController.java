package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.Controller;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.LienHe;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service.LienHeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class LienHeController {

    private final LienHeService service;

    // --- PHẦN CHO USER ---
    @GetMapping("/lienhe")
    public String form(Model model) {
        model.addAttribute("lienHe", new LienHe());
        return "user/lienhe/form";
    }

    @PostMapping("/lienhe/gui")
    public String submit(@ModelAttribute("lienHe") LienHe lh) {
        lh.setThoiGianGui(LocalDateTime.now());
        service.save(lh);
        return "redirect:/lienhe?success";
    }

    // --- PHẦN CHO ADMIN (Đường dẫn gây lỗi 404 của bạn ở đây) ---
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/lienhe/list")
    public String adminList(Model model) {
        model.addAttribute("list", service.findAll());
        return "admin/lienhe/list";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/lienhe/xoa/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/admin/lienhe/list?deleted";
    }
}