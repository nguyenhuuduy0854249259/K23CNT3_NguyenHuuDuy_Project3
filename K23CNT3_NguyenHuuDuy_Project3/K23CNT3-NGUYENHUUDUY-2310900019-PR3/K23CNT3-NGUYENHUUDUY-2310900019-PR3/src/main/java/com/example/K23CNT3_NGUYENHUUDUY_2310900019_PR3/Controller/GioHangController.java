package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.Controller;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.GioHang;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.GioHangChiTiet;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.SanPham;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.NguoiDung;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.NguoiDungRepository;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service.GioHangService;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service.SanPhamService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/giohang")
@RequiredArgsConstructor
public class GioHangController {

    private final GioHangService gioHangService;
    private final SanPhamService sanPhamService;
    private final NguoiDungRepository nguoiDungRepository;

    // ============================
    // XEM GIỎ HÀNG
    // ============================
    @GetMapping
    public String viewCart(Authentication authentication, Model model) {

        if (authentication == null || authentication.getName().equals("anonymousUser")) {
            return "redirect:/auth/login";
        }

        // 🔴 SỬA Ở ĐÂY: username -> email
        NguoiDung user = nguoiDungRepository
                .findByEmail(authentication.getName())
                .orElse(null);

        if (user == null) {
            return "redirect:/auth/login";
        }

        GioHang gioHang = gioHangService.findByNguoiDung(user.getId())
                .orElseGet(() -> {
                    GioHang gh = new GioHang();
                    gh.setId(user.getId());
                    return gioHangService.save(gh);
                });

        model.addAttribute("gioHang", gioHang);
        return "user/giohang/list";
    }

    // ============================
    // THÊM VÀO GIỎ HÀNG
    // ============================
    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId,
                            @RequestParam(defaultValue = "1") Integer soLuong,
                            Authentication authentication) {

        if (authentication == null || authentication.getName().equals("anonymousUser")) {
            return "redirect:/auth/login";
        }

        // 🔴 SỬA Ở ĐÂY
        NguoiDung user = nguoiDungRepository
                .findByEmail(authentication.getName())
                .orElse(null);

        if (user == null) {
            return "redirect:/auth/login";
        }

        GioHang gioHang = gioHangService.findByNguoiDung(user.getId())
                .orElseGet(() -> {
                    GioHang gh = new GioHang();
                    gh.setId(user.getId());
                    return gioHangService.save(gh);
                });

        SanPham sanPham = sanPhamService.findById(productId).orElse(null);
        if (sanPham == null) {
            return "redirect:/sanpham";
        }

        GioHangChiTiet existing = gioHang.findChiTiet(productId);

        if (existing != null) {
            existing.setSoLuong(existing.getSoLuong() + soLuong);
        } else {
            GioHangChiTiet ct = new GioHangChiTiet();
            ct.setSanPham(sanPham);
            ct.setSoLuong(soLuong);
            ct.setGioHang(gioHang);
            gioHang.getChiTietList().add(ct);
        }

        gioHangService.save(gioHang);
        return "redirect:/giohang";
    }

    // ============================
    // CẬP NHẬT SỐ LƯỢNG
    // ============================
    @PostMapping("/update")
    public String updateQuantity(@RequestParam Long productId,
                                 @RequestParam Integer soLuong,
                                 Authentication authentication) {

        if (authentication == null || authentication.getName().equals("anonymousUser")) {
            return "redirect:/auth/login";
        }

        // 🔴 SỬA Ở ĐÂY
        NguoiDung user = nguoiDungRepository
                .findByEmail(authentication.getName())
                .orElse(null);

        if (user == null) {
            return "redirect:/auth/login";
        }

        GioHang gioHang = gioHangService.findByNguoiDung(user.getId()).orElse(null);

        if (gioHang != null) {
            GioHangChiTiet item = gioHang.findChiTiet(productId);
            if (item != null) {
                item.setSoLuong(soLuong);
                gioHangService.save(gioHang);
            }
        }

        return "redirect:/giohang";
    }

    // ============================
    // XÓA SẢN PHẨM
    // ============================
    @PostMapping("/remove")
    public String removeItem(@RequestParam Long productId,
                             Authentication authentication) {

        if (authentication == null || authentication.getName().equals("anonymousUser")) {
            return "redirect:/auth/login";
        }

        NguoiDung user = nguoiDungRepository
                .findByEmail(authentication.getName())
                .orElse(null);

        if (user == null) {
            return "redirect:/auth/login";
        }

        GioHang gioHang = gioHangService
                .findByNguoiDung(user.getId())
                .orElse(null);

        if (gioHang != null) {
            gioHang.removeChiTiet(productId);
            gioHangService.save(gioHang);
        }

        return "redirect:/giohang";
    }
}
