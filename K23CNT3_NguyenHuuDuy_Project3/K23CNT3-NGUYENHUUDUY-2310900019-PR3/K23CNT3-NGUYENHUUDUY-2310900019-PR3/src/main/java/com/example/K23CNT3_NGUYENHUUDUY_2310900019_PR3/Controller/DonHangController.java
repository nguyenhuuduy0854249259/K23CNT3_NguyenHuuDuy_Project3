package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.Controller;


import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.DonHang;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service.DonHangService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/don-hang")
@RequiredArgsConstructor
public class DonHangController {

    private final DonHangService donHangService;

    @GetMapping
    public String list(Model model, Principal principal) {

        Long userId = Long.parseLong(principal.getName());

        model.addAttribute("list", donHangService.findByNguoiDung(userId));

        return "donhang/list";
    }

    @GetMapping("/{maDonHang}")
    public String detail(@PathVariable String maDonHang, Model model) {

        DonHang donHang = donHangService.findByMaDonHang(maDonHang)
                .orElseThrow(() -> new IllegalArgumentException("Mã đơn hàng không tồn tại"));

        model.addAttribute("donHang", donHang);

        return "donhang/detail";
    }
}

