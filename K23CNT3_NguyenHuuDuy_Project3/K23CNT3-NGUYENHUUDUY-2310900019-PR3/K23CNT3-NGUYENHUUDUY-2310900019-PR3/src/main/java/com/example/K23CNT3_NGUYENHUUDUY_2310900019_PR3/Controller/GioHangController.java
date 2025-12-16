package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.Controller;


import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.GioHang;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.GioHangChiTiet;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.SanPham;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service.GioHangService;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service.SanPhamService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/gio-hang")
@RequiredArgsConstructor
public class GioHangController {

    private final GioHangService gioHangService;
    private final SanPhamService sanPhamService;

    @GetMapping("/{userId}")
    public String viewCart(@PathVariable Long userId, Model model) {

        GioHang gioHang = gioHangService.findByNguoiDung(userId)
                .orElse(new GioHang());

        model.addAttribute("gioHang", gioHang);

        return "giohang/view";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long userId,
                            @RequestParam Long productId,
                            @RequestParam(defaultValue = "1") Integer soLuong) {

        GioHang gioHang = gioHangService.findByNguoiDung(userId)
                .orElseGet(() -> {
                    GioHang gh = new GioHang();
                    gh.setNguoiDungId(userId);
                    return gioHangService.save(gh);
                });

        SanPham sanPham = sanPhamService.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Sản phẩm không tồn tại"));

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

        return "redirect:/gio-hang/" + userId;
    }

    @PostMapping("/update")
    public String updateQuantity(@RequestParam Long userId,
                                 @RequestParam Long productId,
                                 @RequestParam Integer soLuong) {

        GioHang gioHang = gioHangService.findByNguoiDung(userId)
                .orElseThrow(() -> new IllegalArgumentException("Giỏ hàng không tồn tại"));

        GioHangChiTiet item = gioHang.findChiTiet(productId);

        if (item != null) {
            item.setSoLuong(soLuong);
            gioHangService.save(gioHang);
        }

        return "redirect:/gio-hang/" + userId;
    }

    @GetMapping("/remove")
    public String removeItem(@RequestParam Long userId,
                             @RequestParam Long productId) {

        GioHang gioHang = gioHangService.findByNguoiDung(userId)
                .orElseThrow(() -> new IllegalArgumentException("Giỏ hàng không tồn tại"));

        gioHang.removeChiTiet(productId);

        gioHangService.save(gioHang);

        return "redirect:/gio-hang/" + userId;
    }
}

