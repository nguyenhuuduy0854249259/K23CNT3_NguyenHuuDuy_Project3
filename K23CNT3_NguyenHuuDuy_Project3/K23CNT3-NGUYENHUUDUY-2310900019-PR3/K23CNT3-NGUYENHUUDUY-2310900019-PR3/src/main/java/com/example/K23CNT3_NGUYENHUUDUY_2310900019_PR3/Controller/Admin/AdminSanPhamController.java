package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.Controller.Admin;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.SanPham;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service.SanPhamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/sanpham")
@RequiredArgsConstructor
public class AdminSanPhamController {

    private final SanPhamService sanPhamService;

    // ===== redirect /admin/sanpham -> /list =====
    @GetMapping
    public String redirectList() {
        return "redirect:/admin/sanpham/list";
    }

    // ===== LIST =====
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("list", sanPhamService.findAll());
        return "admin/sanpham/list";
    }

    // ===== CREATE FORM =====
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("sp", new SanPham());
        model.addAttribute("action", "/admin/sanpham/create");
        model.addAttribute("title", "Thêm sản phẩm");
        return "admin/sanpham/form";
    }

    // ===== CREATE =====
    @PostMapping("/create")
    public String create(@ModelAttribute("sp") SanPham sp) {
        sanPhamService.save(sp);
        return "redirect:/admin/sanpham/list";
    }

    // ===== EDIT FORM =====
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        SanPham sp = sanPhamService.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));
        model.addAttribute("sp", sp);
        model.addAttribute("action", "/admin/sanpham/edit/" + id);
        model.addAttribute("title", "Cập nhật sản phẩm");
        return "admin/sanpham/form";
    }

    // ===== UPDATE =====
    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute("sp") SanPham sp) {
        sp.setId(id);
        sanPhamService.save(sp);
        return "redirect:/admin/sanpham/list";
    }

    // ===== DELETE =====
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        sanPhamService.delete(id);
        return "redirect:/admin/sanpham/list";
    }
}
