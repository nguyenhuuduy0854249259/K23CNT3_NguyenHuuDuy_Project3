package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.Controller;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.DanhGia;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.NguoiDung;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.SanPham;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.DanhGiaRepository;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.DanhMucRepository;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.NguoiDungRepository;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.SanPhamRepository;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service.SanPhamService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/sanpham")
@RequiredArgsConstructor
public class SanPhamController {

    private final SanPhamService sanPhamService;
    private final SanPhamRepository sanPhamRepository;
    private final DanhMucRepository danhMucRepository;
    private final DanhGiaRepository danhGiaRepository;
    private final NguoiDungRepository nguoiDungRepository;

    // ==========================================
    // 1. DANH SÁCH SẢN PHẨM + LỌC (DANH MỤC & THƯƠNG HIỆU)
    // ==========================================
    @GetMapping
    public String list(
            @RequestParam(required = false) Long danhMucId,
            @RequestParam(required = false) Long thuongHieuId,
            Model model
    ) {
        // Xử lý lọc đa năng
        if (thuongHieuId != null) {
            // Lọc theo thương hiệu từ Slider trang chủ
            model.addAttribute("list", sanPhamService.findByThuongHieu(thuongHieuId));
        } else if (danhMucId != null) {
            // Lọc theo danh mục từ Sidebar bên trái
            model.addAttribute("list", sanPhamRepository.findByDanhMuc_Id(danhMucId));
        } else {
            // Mặc định hiển thị tất cả
            model.addAttribute("list", sanPhamRepository.findAll());
        }

        // Luôn gửi danh sách danh mục để hiện ở Sidebar
        model.addAttribute("dsDanhMuc", danhMucRepository.findAll());
        model.addAttribute("title", "Danh sách sản phẩm");

        return "user/sanpham/list";
    }

    // ==========================================
    // 2. CHI TIẾT SẢN PHẨM + ĐÁNH GIÁ
    // ==========================================
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id,
                         Authentication authentication,
                         Model model) {

        return sanPhamRepository.findById(id)
                .map(sp -> {
                    model.addAttribute("sp", sp);

                    // Danh sách đánh giá đã duyệt
                    List<DanhGia> listDanhGia =
                            danhGiaRepository.findBySanPham_IdAndDaDuyetTrueOrderByThoiGianDesc(id);
                    model.addAttribute("listDanhGia", listDanhGia);

                    // Sao trung bình
                    Double avgStar = danhGiaRepository.tinhSaoTrungBinh(id);
                    model.addAttribute("avgStar", avgStar);

                    // Kiểm tra trạng thái đánh giá của User hiện tại
                    boolean daDanhGia = false;
                    if (authentication != null) {
                        nguoiDungRepository.findByUsername(authentication.getName())
                                .ifPresent(user -> {
                                    model.addAttribute("user", user);
                                });

                        NguoiDung user = nguoiDungRepository.findByUsername(authentication.getName()).orElse(null);
                        if (user != null) {
                            daDanhGia = danhGiaRepository.existsBySanPham_IdAndNguoiDung_Id(id, user.getId());
                        }
                    }

                    model.addAttribute("daDanhGia", daDanhGia);
                    model.addAttribute("newDanhGia", new DanhGia());

                    return "user/sanpham/detail";
                })
                .orElse("redirect:/sanpham?notfound");
    }

    // ==========================================
    // 3. GỬI ĐÁNH GIÁ (POST)
    // ==========================================
    @PostMapping("/danhgia/{id}")
    public String submitDanhGia(@PathVariable Long id,
                                @ModelAttribute("newDanhGia") DanhGia danhGia,
                                Authentication authentication) {

        if (authentication == null) return "redirect:/auth/login";

        SanPham sanPham = sanPhamRepository.findById(id).orElse(null);
        NguoiDung nguoiDung = nguoiDungRepository.findByUsername(authentication.getName()).orElse(null);

        if (sanPham == null || nguoiDung == null) return "redirect:/sanpham";

        // Chặn đánh giá trùng
        if (danhGiaRepository.existsBySanPham_IdAndNguoiDung_Id(id, nguoiDung.getId())) {
            return "redirect:/sanpham/" + id + "?rated";
        }

        danhGia.setSanPham(sanPham);
        danhGia.setNguoiDung(nguoiDung);
        danhGia.setThoiGian(LocalDateTime.now());
        danhGia.setDaDuyet(false); // Chờ Admin duyệt

        danhGiaRepository.save(danhGia);

        return "redirect:/sanpham/" + id + "?success";
    }
}