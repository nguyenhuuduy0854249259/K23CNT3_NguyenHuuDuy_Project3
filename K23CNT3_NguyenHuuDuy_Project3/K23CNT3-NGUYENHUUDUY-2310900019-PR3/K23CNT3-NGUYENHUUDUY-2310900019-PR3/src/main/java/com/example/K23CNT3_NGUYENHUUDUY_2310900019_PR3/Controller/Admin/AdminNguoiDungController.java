package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.Controller.Admin;


import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.NguoiDung;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service.NguoiDungService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/nguoidung")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminNguoiDungController {

    private final NguoiDungService nguoiDungService;
    private final PasswordEncoder passwordEncoder;

    // =========================
    // DANH SÁCH
    // =========================
    @GetMapping
    public String list(Model model) {
        model.addAttribute("list", nguoiDungService.findAll());
        return "admin/nguoidung/list";
    }

    // =========================
    // TẠO MỚI
    // =========================
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("user", new NguoiDung());
        return "admin/nguoidung/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("user") NguoiDung user) {

        // encode password (BẮT BUỘC)
        user.setMatKhau(passwordEncoder.encode(user.getMatKhau()));

        nguoiDungService.save(user);
        return "redirect:/admin/nguoidung";
    }

    // =========================
    // CẬP NHẬT
    // =========================
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {

        NguoiDung user = nguoiDungService.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        model.addAttribute("user", user);
        return "admin/nguoidung/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute("user") NguoiDung user) {

        NguoiDung oldUser = nguoiDungService.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setId(id);

        // nếu không nhập mật khẩu mới → giữ mật khẩu cũ
        if (user.getMatKhau() == null || user.getMatKhau().isBlank()) {
            user.setMatKhau(oldUser.getMatKhau());
        } else {
            user.setMatKhau(passwordEncoder.encode(user.getMatKhau()));
        }

        nguoiDungService.save(user);
        return "redirect:/admin/nguoidung";
    }

    // =========================
    // XOÁ (POST – KHÔNG DÙNG GET)
    // =========================
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        nguoiDungService.delete(id);
        return "redirect:/admin/nguoidung";
    }

    // =========================
    // CHI TIẾT
    // =========================
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {

        NguoiDung user = nguoiDungService.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        model.addAttribute("user", user);
        return "admin/nguoidung/detail";
    }
}
