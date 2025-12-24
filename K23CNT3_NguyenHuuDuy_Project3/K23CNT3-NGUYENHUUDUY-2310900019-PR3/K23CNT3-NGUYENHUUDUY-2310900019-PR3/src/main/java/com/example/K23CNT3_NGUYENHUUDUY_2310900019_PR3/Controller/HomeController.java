package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.Controller;


import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.SanPhamRepository;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service.ThuongHieuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private  final ThuongHieuService thuongHieuService;
    private final SanPhamRepository sanPhamRepository;

    @GetMapping({"", "/", "/home"})
    public String home(Model model) {

//        // Lấy toàn bộ sản phẩm từ DB
//        model.addAttribute("listSanPham", sanPhamRepository.findAll());

        return "user/home";
    }

    public String index(Model model) {
        // Lấy tất cả thương hiệu để làm thanh trượt
        model.addAttribute("dsThuongHieu", thuongHieuService.findAll());
        model.addAttribute("title", "Trang chủ");
        return "user/home/index";
    }
}
