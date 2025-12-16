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

    @GetMapping
    public String list(Model model) {
        model.addAttribute("list", sanPhamService.findAll());
        return "admin/sanpham/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("sp", new SanPham());
        return "admin/sanpham/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("sp") SanPham sp) {
        sanPhamService.save(sp);
        return "redirect:/admin/sanpham";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        SanPham sp = sanPhamService.findById(id).orElse(null);
        model.addAttribute("sp", sp);
        return "admin/sanpham/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id, @ModelAttribute("sp") SanPham sp) {
        sp.setId(id);
        sanPhamService.save(sp);
        return "redirect:/admin/sanpham";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        sanPhamService.delete(id);
        return "redirect:/admin/sanpham";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        SanPham sp = sanPhamService.findById(id).orElse(null);
        model.addAttribute("sp", sp);
        return "admin/sanpham/detail";
    }
}

