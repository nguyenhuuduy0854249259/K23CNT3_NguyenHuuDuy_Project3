package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.Controller.Admin;



import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.ThuongHieu;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service.ThuongHieuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/thuonghieu")
@RequiredArgsConstructor
public class AdminThuongHieuController {

    private final ThuongHieuService thuongHieuService;

    // ============================
    // 1. Danh sách thương hiệu
    // ============================
    @GetMapping
    public String list(Model model) {
        model.addAttribute("dsThuongHieu", thuongHieuService.findAll());
        return "admin/thuonghieu/list";  // trang list.html
    }

    // ============================
    // 2. Form thêm mới
    // ============================
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("thuongHieu", new ThuongHieu());
        return "admin/thuonghieu/add";   // trang add.html
    }

    @PostMapping("/add")
    public String add(@ModelAttribute ThuongHieu thuongHieu) {
        thuongHieuService.save(thuongHieu);
        return "redirect:/admin/thuonghieu";
    }

    // ============================
    // 3. Form chỉnh sửa
    // ============================
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        ThuongHieu th = thuongHieuService.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thương hiệu"));
        model.addAttribute("thuongHieu", th);
        return "admin/thuonghieu/edit";   // trang edit.html
    }

    @PostMapping("/edit/{id}")
    public String edit(
            @PathVariable Long id,
            @ModelAttribute ThuongHieu thuongHieu) {

        thuongHieu.setId(id);
        thuongHieuService.save(thuongHieu);

        return "redirect:/admin/thuonghieu";
    }

    // ============================
    // 4. Xóa
    // ============================
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        thuongHieuService.delete(id);
        return "redirect:/admin/thuonghieu";
    }
}
