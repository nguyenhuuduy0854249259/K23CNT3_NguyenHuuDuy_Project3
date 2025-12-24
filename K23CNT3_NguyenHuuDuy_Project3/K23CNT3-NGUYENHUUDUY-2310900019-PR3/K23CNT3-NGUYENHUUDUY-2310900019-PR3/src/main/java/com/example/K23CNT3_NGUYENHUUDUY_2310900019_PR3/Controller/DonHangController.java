package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.Controller;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.DonHang;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.DonHangChiTiet;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.NguoiDung;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.NguoiDungRepository;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service.DonHangChiTietService;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service.DonHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/donhang")
@RequiredArgsConstructor
public class DonHangController {

    private final DonHangService donHangService;
    private final DonHangChiTietService donHangChiTietService;
    private final NguoiDungRepository nguoiDungRepository;

    // 1. DANH SÁCH: http://localhost:8080/donhang
    @GetMapping
    public String list(Model model, Principal principal) {
        if (principal == null) return "redirect:/auth/login";
        NguoiDung user = nguoiDungRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<DonHang> donHangList = donHangService.findByNguoiDung(user.getId());
        model.addAttribute("listDonHang", donHangList);
        return "user/donhang/list";
    }

    // 2. CHI TIẾT: http://localhost:8080/donhang/{id}
    @GetMapping("/{id}")
    public String chiTiet(@PathVariable Long id, Model model) {
        DonHang donHang = donHangService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Đơn hàng không tồn tại: " + id));

        model.addAttribute("donHang", donHang);
        // Lấy list trực tiếp từ Entity DonHang đã có @OneToMany
        model.addAttribute("chiTietList", donHang.getDonHangChiTiet());

        return "user/donhang/detail";
    }
    // 3. HỦY ĐƠN: http://localhost:8080/donhang/huy/MA001
    @PostMapping("/huy/{maDonHang}")
    public String huyDonHang(@PathVariable String maDonHang, Principal principal) {
        if (principal == null) return "redirect:/auth/login";

        DonHang donHang = donHangService.findByMaDonHang(maDonHang)
                .orElseThrow(() -> new IllegalArgumentException("Đơn hàng không tồn tại"));

        // Kiểm tra nếu đơn hàng đang ở trạng thái có thể hủy (ví dụ: Đang xử lý)
        if ("Đang xử lý".equals(donHang.getTrangThai()) || "CHO_XU_LY".equals(donHang.getTrangThai())) {
            donHang.setTrangThai("Đã hủy"); // Nên dùng tiếng Việt nếu các trạng thái khác dùng tiếng Việt
            donHangService.save(donHang);
        }

        return "redirect:/donhang";
    }
}