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

    @GetMapping
    public String checkoutForm(Authentication authentication, Model model) {
        if (authentication == null || authentication.getName().equals("anonymousUser")) {
            return "redirect:/auth/login";
        }

        NguoiDung user = nguoiDungRepository.findByEmail(authentication.getName()).orElse(null);
        if (user == null) return "redirect:/auth/login";

        GioHang gioHang = gioHangService.findByNguoiDung(user.getId()).orElse(new GioHang());

        double tongTien = gioHang.getChiTietList().stream()
                .mapToDouble(ct -> ct.getSanPham().getGia() * ct.getSoLuong())
                .sum();

        model.addAttribute("loggedUser", user);
        model.addAttribute("cartItems", gioHang.getChiTietList());
        model.addAttribute("tongTien", tongTien);

        return "user/checkout/index";
    }

    @PostMapping
    public String placeOrder(Authentication authentication,
                             @RequestParam String hoTen,
                             @RequestParam String diaChi,
                             @RequestParam String soDienThoai) {

        if (authentication == null || authentication.getName().equals("anonymousUser")) {
            return "redirect:/auth/login";
        }

        NguoiDung user = nguoiDungRepository.findByEmail(authentication.getName()).orElse(null);
        if (user == null) return "redirect:/auth/login";

        GioHang gioHang = gioHangService.findByNguoiDung(user.getId()).orElse(new GioHang());
        if (gioHang.getChiTietList().isEmpty()) {
            return "redirect:/giohang";
        }

        // 1. TẠO ĐỐI TƯỢNG ĐƠN HÀNG
        DonHang donHang = DonHang.builder()
                .maDonHang(UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .ngayDat(LocalDateTime.now())
                .tongTien(gioHang.getChiTietList().stream()
                        .mapToDouble(ct -> ct.getSanPham().getGia() * ct.getSoLuong())
                        .sum())
                .tenNguoiNhan(hoTen)        // Khớp với Entity
                .soDienThoaiNhan(soDienThoai) // Khớp với Entity
                .diaChiNhan(diaChi)         // Khớp với Entity
                .phuongThucThanhToan("Thanh toán khi nhận hàng (COD)")
                .trangThai("Đang xử lý")
                .nguoiDung(user)
                .build();

        // 2. LƯU ĐƠN HÀNG TRƯỚC (Để sinh ID vào Database)
        // Đây là bước quan trọng nhất để sửa lỗi TransientObjectException
        DonHang savedDonHang = donHangService.save(donHang);

        // 3. CHUYỂN CHI TIẾT TỪ GIỎ SANG ĐƠN HÀNG (Sử dụng savedDonHang đã có ID)
        gioHang.getChiTietList().forEach(ct -> {
            donHangService.addChiTietDonHang(savedDonHang, ct);
        });

        // 4. XÓA GIỎ HÀNG SAU KHI ĐẶT THÀNH CÔNG
        // Lưu ý: Tùy vào thiết kế DB, bạn có thể cần xóa chi tiết trước rồi mới clear list
        // Nếu Repo của bạn hỗ trợ deleteAllByGioHangId thì dùng nó sẽ sạch hơn.
        gioHang.getChiTietList().clear();
        gioHangService.save(gioHang);

        return "redirect:/checkout/success";
    }

    @GetMapping("/success")
    public String successPage() {
        return "user/checkout/success";
    }
}