package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.Controller;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.DonHang;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.DonHangChiTiet;
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

    /**
     * 1. DANH SÁCH ĐƠN HÀNG (USER)
     */
    @GetMapping
    public String list(Model model, Principal principal) {

        Long userId = Long.parseLong(principal.getName());

        List<DonHang> donHangList = donHangService.findByNguoiDung(userId);
        model.addAttribute("list", donHangList);

        return "donhang/list";
    }

    /**
     * 2. CHI TIẾT ĐƠN HÀNG (USER)
     */
    @GetMapping("/chitiet/{maDonHang}")
    public String chiTiet(@PathVariable String maDonHang, Model model) {

        DonHang donHang = donHangService.findByMaDonHang(maDonHang)
                .orElseThrow(() -> new IllegalArgumentException("Đơn hàng không tồn tại"));

        List<DonHangChiTiet> chiTietList =
                donHangChiTietService.findByDonHangId(donHang.getId());

        model.addAttribute("donHang", donHang);
        model.addAttribute("chiTietList", chiTietList);

        return "user/donhang/detail";
    }

    /**
     * 3. HỦY ĐƠN HÀNG (USER)
     */
    @PostMapping("/huy/{maDonHang}")
    public String huyDonHang(@PathVariable String maDonHang, Principal principal) {

        DonHang donHang = donHangService.findByMaDonHang(maDonHang)
                .orElseThrow(() -> new IllegalArgumentException("Đơn hàng không tồn tại"));

        Long userId = Long.parseLong(principal.getName());

        // đảm bảo user chỉ hủy đơn của mình
        if (!donHang.getNguoiDung().getId().equals(userId)) {
            throw new SecurityException("Không có quyền hủy đơn hàng này");
        }

        // chỉ cho hủy khi chưa giao
        if (!donHang.getTrangThai().equals("CHO_XU_LY")) {
            throw new IllegalStateException("Không thể hủy đơn hàng này");
        }

        donHang.setTrangThai("DA_HUY");
        donHangService.save(donHang);

        return "redirect:/donhang";
    }
}
