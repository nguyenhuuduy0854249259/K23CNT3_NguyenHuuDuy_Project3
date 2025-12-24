package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.Controller.Admin;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.DonHang;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service.DonHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/donhang")
@RequiredArgsConstructor
public class AdminDonHangController {

    private final DonHangService donHangService;

    // 1. Hiển thị danh sách toàn bộ đơn hàng (Sửa lỗi lọc theo null)
    @GetMapping({"", "/list"})
    public String list(Model model) {
        List<DonHang> allOrders = donHangService.findAll();
        model.addAttribute("list", allOrders);
        return "admin/donhang/list";
    }

    // 2. Chi tiết đơn hàng theo ID
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        DonHang dh = donHangService.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng mã: " + id));

        model.addAttribute("dh", dh);
        return "admin/donhang/detail";
    }

    // 3. Tìm kiếm đơn hàng theo mã đơn (Hiển thị thẳng trang detail nếu thấy)
    @GetMapping("/search")
    public String searchByCode(@RequestParam String ma, Model model) {
        DonHang dh = donHangService.findByMaDonHang(ma).orElse(null);

        if (dh == null) {
            model.addAttribute("error", "Không tìm thấy mã đơn hàng này!");
            return "admin/donhang/list"; // Hoặc trang báo lỗi
        }

        model.addAttribute("dh", dh);
        return "admin/donhang/detail";
    }

    // 4. Cập nhật trạng thái đơn hàng (Sửa lỗi logic tìm kiếm theo User)
    @PostMapping("/update-status")
    public String updateStatus(@RequestParam Long id, @RequestParam String trangThai) {
        // Tìm trực tiếp theo ID đơn hàng
        DonHang dh = donHangService.findById(id).orElse(null);

        if (dh != null) {
            dh.setTrangThai(trangThai);
            donHangService.save(dh);
        }

        // Chuyển hướng về trang chi tiết sau khi cập nhật
        return "redirect:/admin/donhang/detail/" + id;
    }
}