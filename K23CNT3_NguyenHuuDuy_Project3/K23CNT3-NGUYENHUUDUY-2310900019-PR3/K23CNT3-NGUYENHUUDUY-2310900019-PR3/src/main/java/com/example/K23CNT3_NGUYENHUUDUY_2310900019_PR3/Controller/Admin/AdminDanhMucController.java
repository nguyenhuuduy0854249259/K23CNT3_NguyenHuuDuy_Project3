package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.Controller.Admin;


import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.DanhMuc;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service.DanhMucService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/danh-muc")
@RequiredArgsConstructor
public class AdminDanhMucController {

    private final DanhMucService danhMucService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("list", danhMucService.findAll());
        return "admin/danhmuc/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("dm", new DanhMuc());
        return "admin/danhmuc/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute DanhMuc dm) {
        danhMucService.save(dm);
        return "redirect:/admin/danh-muc";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        DanhMuc dm = danhMucService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Danh mục không tồn tại"));
        model.addAttribute("dm", dm);
        return "admin/danhmuc/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        danhMucService.delete(id);
        return "redirect:/admin/danh-muc";
    }
}

