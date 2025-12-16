package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.Controller;


import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.SanPhamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final SanPhamRepository sanPhamRepository;

    @GetMapping({"", "/", "/home"})
    public String home(Model model) {

        // Lấy toàn bộ sản phẩm từ DB
        model.addAttribute("listSanPham", sanPhamRepository.findAll());

        return "user/home";
    }
}
