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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping
    public String list(@RequestParam(required = false) Long danhMucId,
                       @RequestParam(required = false) Long thuongHieuId,
                       Model model) {
        // QUAN TRỌNG: Nạp danh mục cho Sidebar
        model.addAttribute("dsDanhMuc", danhMucRepository.findAll());

        String title = "Tất cả sản phẩm";
        if (thuongHieuId != null) {
            model.addAttribute("list", sanPhamService.findByThuongHieu(thuongHieuId));
            title = "Sản phẩm theo thương hiệu";
        } else if (danhMucId != null) {
            model.addAttribute("list", sanPhamRepository.findByDanhMuc_Id(danhMucId));
            title = danhMucRepository.findById(danhMucId).map(dm -> dm.getTen()).orElse("Danh mục");
        } else {
            model.addAttribute("list", sanPhamRepository.findAll());
        }
        model.addAttribute("title", title);
        return "user/sanpham/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Authentication authentication, Model model) {
        // QUAN TRỌNG: Nạp danh mục cho Sidebar
        model.addAttribute("dsDanhMuc", danhMucRepository.findAll());

        return sanPhamRepository.findById(id).map(sp -> {
            model.addAttribute("sp", sp);
            model.addAttribute("listDanhGia", danhGiaRepository.findBySanPham_IdAndDaDuyetTrueOrderByThoiGianDesc(id));
            model.addAttribute("avgStar", danhGiaRepository.tinhSaoTrungBinh(id));

            boolean daDanhGia = false;
            if (authentication != null) {
                NguoiDung user = nguoiDungRepository.findByUsername(authentication.getName()).orElse(null);
                if (user != null) {
                    daDanhGia = danhGiaRepository.existsBySanPham_IdAndNguoiDung_Id(id, user.getId());
                    model.addAttribute("user", user);
                }
            }
            model.addAttribute("daDanhGia", daDanhGia);
            model.addAttribute("newDanhGia", new DanhGia());
            return "user/sanpham/detail";
        }).orElse("redirect:/sanpham?notfound");
    }

    @PostMapping("/danhgia/{id}")
    public String submitDanhGia(@PathVariable Long id,
                                @ModelAttribute("newDanhGia") DanhGia danhGia,
                                Authentication authentication,
                                RedirectAttributes ra) {
        if (authentication == null) return "redirect:/auth/login";

        SanPham sanPham = sanPhamRepository.findById(id).orElse(null);
        NguoiDung nguoiDung = nguoiDungRepository.findByUsername(authentication.getName()).orElse(null);

        if (sanPham != null && nguoiDung != null) {
            if (danhGiaRepository.existsBySanPham_IdAndNguoiDung_Id(id, nguoiDung.getId())) {
                ra.addFlashAttribute("error", "Bạn đã gửi đánh giá cho sản phẩm này rồi.");
            } else {
                danhGia.setSanPham(sanPham);
                danhGia.setNguoiDung(nguoiDung);
                danhGia.setThoiGian(LocalDateTime.now());
                danhGia.setDaDuyet(true);
                danhGiaRepository.save(danhGia);
                ra.addFlashAttribute("message", "Cảm ơn bạn đã đánh giá!");
            }
        }
        return "redirect:/sanpham/" + id;
    }
}