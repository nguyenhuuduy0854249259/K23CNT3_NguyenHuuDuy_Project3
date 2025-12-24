package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.Controller.Admin;


import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.DanhGia;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.DanhGiaRepository;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.SanPhamRepository;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service.DanhGiaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/danhgia")
@RequiredArgsConstructor
public class AdminDanhGiaController {

    private  final DanhGiaRepository  danhGiaRepository;
    private final DanhGiaService danhGiaService;
    private final SanPhamRepository sanPhamRepository;

    // Hiển thị danh sách tất cả đánh giá
    @GetMapping
    public String list(Model model) {
        model.addAttribute("dsDanhGia", danhGiaService.getAll());
        model.addAttribute("title", "Quản lý đánh giá");
        return "admin/danhgia/list";
    }

    // Duyệt đánh giá (Chuyển daDuyet từ false -> true)
    @GetMapping("/duyet/{id}")
    public String duyet(@PathVariable Long id) {
        danhGiaService.duyet(id);
        return "redirect:/admin/danhgia?approved";
    }

    // Xóa đánh giá (Nếu nội dung vi phạm hoặc spam)
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        danhGiaService.xoa(id);
        return "redirect:/admin/danhgia?deleted";
    }

    @GetMapping("/sanpham/{id}")
    public String listBySanPham(@PathVariable Long id, Model model) {
        // 1. Lấy danh sách đánh giá của SP này (nên dùng hàm OrderByThoiGianDesc)
        List<DanhGia> ds = danhGiaRepository.findBySanPham_IdOrderByThoiGianDesc(id);

        // 2. Tìm tên sản phẩm để hiển thị tiêu đề (cho Admin dễ biết đang xem SP nào)
        String tenSp = sanPhamRepository.findById(id)
                .map(sp -> sp.getTen()) // dùng .getTen() theo entity của bạn
                .orElse("Sản phẩm");

        model.addAttribute("dsDanhGia", ds);
        model.addAttribute("title", "Đánh giá cho sản phẩm: " + tenSp);

        return "admin/danhgia/list"; // Trả về trang list.html của thư mục danhgia
    }
}