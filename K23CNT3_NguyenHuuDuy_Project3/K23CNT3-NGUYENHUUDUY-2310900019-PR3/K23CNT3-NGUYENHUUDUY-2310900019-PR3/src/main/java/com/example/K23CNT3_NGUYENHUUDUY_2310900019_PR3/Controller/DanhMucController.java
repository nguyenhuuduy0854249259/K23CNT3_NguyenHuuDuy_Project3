package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.Controller;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.DanhMuc;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service.DanhMucService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/danhmuc")
@RequiredArgsConstructor
public class DanhMucController {

    private final DanhMucService danhMucService;

    // ============================
    // 1. HIỂN THỊ DANH SÁCH
    // ============================
    @GetMapping
    public String listDanhMuc(Model model) {
        model.addAttribute("listDanhMuc", danhMucService.findAll());
        return "danhmuc/list"; // templates/danhmuc/list.html
    }

    // ============================
    // 2. FORM THÊM MỚI
    // ============================
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("dm", new DanhMuc()); // đặt tên 'dm' cho template
        return "danhmuc/add"; // templates/danhmuc/add.html
    }

    // SUBMIT THÊM MỚI
    @PostMapping("/add")
    public String addDanhMuc(@ModelAttribute("dm") DanhMuc danhMuc) {
        danhMucService.save(danhMuc);
        return "redirect:/danhmuc";
    }

    // ============================
    // 3. FORM CẬP NHẬT
    // ============================
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        DanhMuc dm = danhMucService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Danh mục không tồn tại: " + id));

        model.addAttribute("dm", dm); // đặt tên 'dm' cho template
        return "danhmuc/edit"; // templates/danhmuc/edit.html
    }

    // SUBMIT CẬP NHẬT
    @PostMapping("/edit/{id}")
    public String updateDanhMuc(@PathVariable Long id,
                                @ModelAttribute("dm") DanhMuc danhMuc) {
        danhMuc.setId(id); // đảm bảo ID đúng
        danhMucService.save(danhMuc);
        return "redirect:/danhmuc";
    }

    // ============================
    // 4. XÓA DANH MỤC
    // ============================
    @GetMapping("/delete/{id}")
    public String deleteDanhMuc(@PathVariable Long id) {
        danhMucService.delete(id);
        return "redirect:/danhmuc";
    }
}
