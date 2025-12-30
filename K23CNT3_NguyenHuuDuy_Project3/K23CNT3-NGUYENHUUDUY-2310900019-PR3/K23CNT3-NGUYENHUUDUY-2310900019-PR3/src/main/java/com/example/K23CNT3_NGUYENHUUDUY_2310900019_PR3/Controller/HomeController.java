package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.Controller;


import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.SanPham;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.DanhMucRepository;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.SanPhamRepository;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service.ThuongHieuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private  final ThuongHieuService thuongHieuService;
    private final SanPhamRepository sanPhamRepository;
    ;
    private final DanhMucRepository danhMucRepository; // Cần thiết cho Sidebar

    @GetMapping({"", "/", "/home"})
    public String home(Model model) {
        // 1. Lấy danh sách thương hiệu
        model.addAttribute("dsThuongHieu", thuongHieuService.findAll());

        // 2. Lấy danh mục cho Sidebar
        model.addAttribute("dsDanhMuc", danhMucRepository.findAll());

        // 3. Lấy 5 SẢN PHẨM MỚI NHẤT (Sắp xếp theo ID giảm dần)
        // Dùng PageRequest để giới hạn số lượng
        List<SanPham> top5SanPham = sanPhamRepository.findAll(
                org.springframework.data.domain.PageRequest.of(0, 5, org.springframework.data.domain.Sort.by("id").descending())
        ).getContent();

        // Đặt tên biến là dsSanPham để khớp với HTML của bạn
        model.addAttribute("dsSanPham", top5SanPham);

        model.addAttribute("title", "Trang chủ - Thế giới vật liệu");
        return "user/home";
    }

    public String index(Model model) {
        // Lấy tất cả thương hiệu để làm thanh trượt
        model.addAttribute("dsThuongHieu", thuongHieuService.findAll());
        model.addAttribute("title", "Trang chủ");
        return "user/home/index";
    }
}
