package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.Controller;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.TinTuc;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service.TinTucService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/tin-tuc")
@RequiredArgsConstructor
public class TinTucController {

    private final TinTucService tinTucService;

    // =========================================
    // 1. Danh sách tin tức
    // =========================================
    @GetMapping
    public String list(Model model) {
        model.addAttribute("dsTinTuc", tinTucService.findAll());
        return "admin/tintuc/list"; // list.html
    }

    // =========================================
    // 2. Form thêm mới
    // =========================================
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("tinTuc", new TinTuc());
        return "admin/tintuc/add"; // add.html
    }

    @PostMapping("/add")
    public String add(@ModelAttribute TinTuc tinTuc) {
        tinTucService.save(tinTuc);
        return "redirect:/admin/tin-tuc";
    }

    // =========================================
    // 3. Form chỉnh sửa
    // =========================================
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        TinTuc t = tinTucService.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tin tức"));
        model.addAttribute("tinTuc", t);
        return "admin/tintuc/edit"; // edit.html
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute TinTuc tinTuc) {
        tinTuc.setId(id);
        tinTucService.save(tinTuc);
        return "redirect:/admin/tin-tuc";
    }

    // =========================================
    // 4. Xóa tin tức
    // =========================================
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        tinTucService.delete(id);
        return "redirect:/admin/tin-tuc";
    }
}
