package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.Controller;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.DonHang;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.GioHang;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.NguoiDung;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.NguoiDungRepository;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service.DonHangService;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service.GioHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@RequestMapping("/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final NguoiDungRepository nguoiDungRepository;
    private final GioHangService gioHangService;
    private final DonHangService donHangService;

    // =========================
    // HIỂN THỊ TRANG CHECKOUT
    // =========================
    @GetMapping
    public String checkoutForm(Authentication authentication, Model model) {

        if (authentication == null || authentication.getName().equals("anonymousUser")) {
            return "redirect:/auth/login";
        }

        NguoiDung user = nguoiDungRepository
                .findByTenDangNhap(authentication.getName())
                .orElse(null);

        if (user == null) {
            return "redirect:/auth/login";
        }

        GioHang gioHang = gioHangService.findByNguoiDung(user.getId())
                .orElse(new GioHang());

        double tongTien = gioHang.getChiTietList().stream()
                .mapToDouble(ct -> ct.getSanPham().getGia() * ct.getSoLuong())
                .sum();

        model.addAttribute("loggedUser", user);
        model.addAttribute("cartItems", gioHang.getChiTietList());
        model.addAttribute("tongTien", tongTien);

        return "user/checkout/list"; // file checkout.html
    }

    // =========================
    // XỬ LÝ ĐẶT HÀNG
    // =========================
    @PostMapping
    public String placeOrder(Authentication authentication,
                             @RequestParam String hoTen,
                             @RequestParam String diaChi,
                             @RequestParam String soDienThoai) {

        if (authentication == null || authentication.getName().equals("anonymousUser")) {
            return "redirect:/auth/login";
        }

        NguoiDung user = nguoiDungRepository
                .findByTenDangNhap(authentication.getName())
                .orElse(null);

        if (user == null) {
            return "redirect:/auth/login";
        }

        GioHang gioHang = gioHangService.findByNguoiDung(user.getId())
                .orElse(new GioHang());

        if (gioHang.getChiTietList().isEmpty()) {
            return "redirect:/giohang"; // nếu không có sản phẩm
        }

        // Tạo đơn hàng
        DonHang donHang = DonHang.builder()
                .maDonHang(UUID.randomUUID().toString())
                .ngayDat(LocalDateTime.now())
                .tongTien(gioHang.getChiTietList().stream()
                        .mapToDouble(ct -> ct.getSanPham().getGia() * ct.getSoLuong())
                        .sum())
                .tenNguoiNhan(hoTen)
                .soDienThoaiNhan(soDienThoai)
                .diaChiNhan(diaChi)
                .trangThai("Đang xử lý")
                .nguoiDung(user)
                .build();

        // Chuyển chi tiết giỏ hàng sang chi tiết đơn hàng
        gioHang.getChiTietList().forEach(ct -> {
            donHangService.addChiTietDonHang(donHang, ct);
        });

        donHangService.save(donHang);

        // Xóa giỏ hàng sau khi đặt
        gioHang.getChiTietList().clear();
        gioHangService.save(gioHang);

        return "redirect:/checkout/success";
    }

    // =========================
    // TRANG THÀNH CÔNG
    // =========================
    @GetMapping("/success")
    public String successPage() {
        return "user/checkout/success";
    }
}
