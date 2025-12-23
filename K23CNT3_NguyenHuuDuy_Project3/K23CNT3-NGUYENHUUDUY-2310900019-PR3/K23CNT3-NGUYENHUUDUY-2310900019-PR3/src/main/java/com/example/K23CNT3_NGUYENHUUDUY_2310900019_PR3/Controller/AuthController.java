package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.Controller;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.NguoiDung;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service.NguoiDungService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final NguoiDungService nguoiDungService;
    private final PasswordEncoder passwordEncoder;

    // ===== LOGIN PAGE =====
    @GetMapping("/auth/login")
    public String loginPage() {
        return "user/auth/login";
    }

    // ===== REGISTER PAGE =====
    @GetMapping("/auth/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new NguoiDung());
        return "user/auth/register";
    }

    // ===== SUBMIT REGISTER =====
    @PostMapping("/auth/register")
    public String submitRegister(@ModelAttribute("user") NguoiDung user) {
        user.setMatKhau(passwordEncoder.encode(user.getMatKhau()));
        nguoiDungService.save(user);
        return "redirect:/auth/login?success";
    }
}
