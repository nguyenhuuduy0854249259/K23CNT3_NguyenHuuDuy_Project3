package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.Controller;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.DanhGia;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.NguoiDung;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.SanPham;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.DanhGiaRepository;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.DanhMucRepository;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.NguoiDungRepository;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.SanPhamRepository;
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

    private final SanPhamRepository sanPhamRepository;
    private final DanhMucRepository danhMucRepository;
    private final DanhGiaRepository danhGiaRepository;
    private final NguoiDungRepository nguoiDungRepository;

    // =========================
    // DANH SÁCH SẢN PHẨM + LỌC
    // =========================
    @GetMapping
    public String list(
            @RequestParam(required = false) Long danhMucId,
            Model model
    ) {

        if (danhMucId != null) {
            model.addAttribute("list",
                    sanPhamRepository.findByDanhMuc_Id(danhMucId));
        } else {
            model.addAttribute("list",
                    sanPhamRepository.findAll());
        }

        model.addAttribute("dsDanhMuc",
                danhMucRepository.findAll());

        return "user/sanpham/list";
    }

    // =========================
    // CHI TIẾT SẢN PHẨM + ĐÁNH GIÁ
    // =========================
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id,
                         Authentication authentication,
                         Model model) {

        return sanPhamRepository.findById(id)
                .map(sp -> {

                    model.addAttribute("sp", sp);

                    // 1️⃣ Danh sách đánh giá đã duyệt
                    List<DanhGia> listDanhGia =
                            danhGiaRepository
                                    .findBySanPham_IdAndDaDuyetTrueOrderByThoiGianDesc(id);

                    model.addAttribute("listDanhGia", listDanhGia);

                    // 2️⃣ Sao trung bình
                    Double avgStar = danhGiaRepository.tinhSaoTrungBinh(id);
                    model.addAttribute("avgStar", avgStar);

                    // 3️⃣ Kiểm tra user đã đánh giá chưa
                    boolean daDanhGia = false;

                    if (authentication != null) {
                        String tenDangNhap = authentication.getName();

                        NguoiDung user =
                                nguoiDungRepository
                                        .findByUsername(tenDangNhap)
                                        .orElse(null);

                        if (user != null) {
                            daDanhGia = danhGiaRepository
                                    .existsBySanPham_IdAndNguoiDung_Id(
                                            id, user.getId());
                        }
                    }

                    model.addAttribute("daDanhGia", daDanhGia);
                    model.addAttribute("newDanhGia", new DanhGia());

                    return "user/sanpham/detail";
                })
                .orElse("redirect:/sanpham?notfound");
    }

    // =========================
    // GỬI ĐÁNH GIÁ
    // =========================
    @PostMapping("/danhgia/{id}")
    public String submitDanhGia(@PathVariable Long id,
                                @ModelAttribute("newDanhGia") DanhGia danhGia,
                                Authentication authentication) {

        if (authentication == null) {
            return "redirect:/login";
        }

        SanPham sanPham = sanPhamRepository.findById(id).orElse(null);
        if (sanPham == null) {
            return "redirect:/sanpham";
        }

        String tenDangNhap = authentication.getName();

        NguoiDung nguoiDung =
                nguoiDungRepository
                        .findByUsername(tenDangNhap)
                        .orElse(null);

        if (nguoiDung == null) {
            return "redirect:/login";
        }

        // ❌ Chặn đánh giá trùng
        if (danhGiaRepository
                .existsBySanPham_IdAndNguoiDung_Id(
                        id, nguoiDung.getId())) {

            return "redirect:/sanpham/" + id + "?rated";
        }

        danhGia.setSanPham(sanPham);
        danhGia.setNguoiDung(nguoiDung);
        danhGia.setThoiGian(LocalDateTime.now());
        danhGia.setDaDuyet(false); // chờ admin duyệt

        danhGiaRepository.save(danhGia);

        return "redirect:/sanpham/" + id + "?success";
    }
}
