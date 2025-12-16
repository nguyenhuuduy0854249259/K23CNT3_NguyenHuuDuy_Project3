package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.Controller.Admin;


import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.DonHang;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service.DonHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/donhang")
@RequiredArgsConstructor
public class AdminDonHangController {

    private final DonHangService donHangService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("list", donHangService.findByNguoiDung(null)); // nếu muốn load tất cả thì sửa service
        return "admin/donhang/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        DonHang dh = donHangService.findByNguoiDung(id)
                .stream()
                .findFirst()
                .orElse(null);

        model.addAttribute("dh", dh);
        return "admin/donhang/detail";
    }

    @GetMapping("/search")
    public String searchByCode(@RequestParam String ma, Model model) {
        DonHang dh = donHangService.findByMaDonHang(ma)
                .orElse(null);

        model.addAttribute("dh", dh);
        return "admin/donhang/detail";
    }

    @PostMapping("/update-status")
    public String updateStatus(@RequestParam Long id,
                               @RequestParam String trangThai) {

        DonHang dh = donHangService.findByNguoiDung(id)
                .stream()
                .findFirst()
                .orElse(null);

        if (dh != null) {
            dh.setTrangThai(trangThai);
            donHangService.save(dh);
        }

        return "redirect:/admin/donhang/" + id;
    }
}
