package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.Controller.Admin;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service.DonHangService;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service.NguoiDungService;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service.SanPhamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final SanPhamService sanPhamService;
    private final DonHangService donHangService;
    private final NguoiDungService nguoiDungService;

    @GetMapping
    public String dashboard(Model model) {

        model.addAttribute("totalProducts", sanPhamService.count());
        model.addAttribute("totalOrders", donHangService.count());
        model.addAttribute("totalUsers", nguoiDungService.count());

        return "admin/dashboard";
    }
}
