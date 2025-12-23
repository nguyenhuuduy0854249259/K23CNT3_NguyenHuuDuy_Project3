package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.Controller.Admin;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.DanhMuc;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service.DanhMucService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/danhmuc")
@RequiredArgsConstructor
public class AdminDanhMucController {

    private final DanhMucService danhMucService;

    // ===== redirect /admin/danhmuc -> /list =====
//    @GetMapping
//    public String redirectList() {
//        return "redirect:/admin/danhmuc/list";
//    }

    // ===== LIST =====
    @GetMapping
    public String list(Model model) {
        model.addAttribute("listDanhMuc", danhMucService.findAll());
        return "admin/danhmuc/list";
    }

    // ===== CREATE =====
    @GetMapping("/them")
    public String createForm(Model model) {
        model.addAttribute("dm", new DanhMuc());
        model.addAttribute("listDanhMuc", danhMucService.findAll());
        return "admin/danhmuc/form";
    }

    // ===== SAVE =====
    @PostMapping("/luu")
    public String save(@ModelAttribute("dm") DanhMuc dm) {
        danhMucService.save(dm);
        return "redirect:/admin/danhmuc";
    }

    // ===== EDIT =====
    @GetMapping("/sua/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        DanhMuc dm = danhMucService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Danh mục không tồn tại"));

        model.addAttribute("dm", dm);
        model.addAttribute("listDanhMuc", danhMucService.findAll());
        return "admin/danhmuc/form";
    }

    // ===== DELETE =====
    @GetMapping("/xoa/{id}")
    public String delete(@PathVariable Long id) {
        danhMucService.delete(id);
        return "redirect:/admin/danhmuc";
    }
}
