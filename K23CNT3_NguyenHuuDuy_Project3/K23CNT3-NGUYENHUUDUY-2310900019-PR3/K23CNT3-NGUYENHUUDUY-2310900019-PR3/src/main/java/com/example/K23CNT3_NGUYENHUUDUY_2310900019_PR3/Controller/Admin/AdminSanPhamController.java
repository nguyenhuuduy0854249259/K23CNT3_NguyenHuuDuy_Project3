package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.Controller.Admin;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.DanhGia;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.NguoiDung;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.SanPham;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.DanhGiaRepository;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.NguoiDungRepository;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.SanPhamRepository;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service.DanhMucService;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service.SanPhamService;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service.ThuongHieuService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@RequestMapping("/admin/sanpham")
@RequiredArgsConstructor
public class AdminSanPhamController {

    private final SanPhamService sanPhamService;
    private final SanPhamRepository sanPhamRepository;
    private final DanhMucService danhMucService;
    private final ThuongHieuService thuongHieuService;
    private final NguoiDungRepository nguoiDungRepository;
    private final DanhGiaRepository danhGiaRepository;

    // Lấy giá trị từ application.properties
    @Value("${upload.dir}")
    private String uploadDir;

    private void addCommonData(Model model) {
        model.addAttribute("listDanhMuc", danhMucService.findAll());
        model.addAttribute("listThuongHieu", thuongHieuService.findAll());
    }

    // --- HÀM HỖ TRỢ UPLOAD FILE ---
    private String handleFileUpload(MultipartFile file) {
        if (file.isEmpty()) return null;

        try {
            // Tạo tên file duy nhất: UUID + Tên gốc
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            // Đường dẫn lưu file
            Path path = Paths.get(uploadDir);

            // Nếu thư mục chưa tồn tại thì tạo mới
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            // Lưu file vào thư mục
            Files.copy(file.getInputStream(), path.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Hoặc ném ngoại lệ tùy ý
        }
    }

    @GetMapping({"", "/list"})
    public String list(Model model) {
        model.addAttribute("list", sanPhamService.findAll());
        return "admin/sanpham/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        addCommonData(model);
        model.addAttribute("sp", new SanPham());
        model.addAttribute("action", "/admin/sanpham/create");
        model.addAttribute("title", "Thêm sản phẩm mới");
        return "admin/sanpham/form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("sp") SanPham sp,
                         @RequestParam("file") MultipartFile file) {

        // Xử lý upload file
        if (file != null && !file.isEmpty()) {
            String fileName = handleFileUpload(file);
            sp.setHinhAnh(fileName); // Lưu tên file vào database
        }

        sanPhamService.save(sp);
        return "redirect:/admin/sanpham/list";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        SanPham sp = sanPhamService.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        addCommonData(model);
        model.addAttribute("sp", sp);
        model.addAttribute("action", "/admin/sanpham/edit/" + id);
        model.addAttribute("title", "Cập nhật sản phẩm: " + sp.getTen());

        // Truyền ảnh hiện tại sang view để hiển thị preview
        model.addAttribute("currentImage", sp.getHinhAnh());

        return "admin/sanpham/form";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute("sp") SanPham sp,
                         @RequestParam("file") MultipartFile file) { // Thêm tham số file

        // Lấy thông tin sản phẩm cũ từ DB để giữ lại ảnh cũ nếu không upload ảnh mới
        SanPham oldSp = sanPhamService.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        if (file != null && !file.isEmpty()) {
            // 1. Nếu có file mới được upload -> Lưu file mới
            String fileName = handleFileUpload(file);
            sp.setHinhAnh(fileName);
        } else {
            // 2. Nếu không chọn file mới -> Giữ nguyên ảnh cũ
            sp.setHinhAnh(oldSp.getHinhAnh());
        }

        sp.setId(id); // Đảm bảo đúng ID để update
        sanPhamService.save(sp);
        return "redirect:/admin/sanpham/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        // (Tùy chọn) Xóa file ảnh vật lý khỏi ổ cứng trước khi xóa trong DB
        // SanPham sp = sanPhamService.findById(id).orElse(null);
        // if(sp != null && sp.getHinhAnh() != null) { ... logic xóa file ... }

        sanPhamService.delete(id);
        return "redirect:/admin/sanpham/list";
    }
    // ==========================================
    // 3. GỬI ĐÁNH GIÁ (POST)
    // ==========================================
    // Sửa lại hàm PostMapping một chút
//    @PostMapping("/danhgia/{id}")
//    public String submitDanhGia(@PathVariable Long id,
//                                @ModelAttribute("newDanhGia") DanhGia danhGia,
//                                Authentication authentication,
//                                RedirectAttributes ra) {
//
//        if (authentication == null) return "redirect:/auth/login";
//
//        SanPham sanPham = sanPhamRepository.findById(id).orElse(null);
//        NguoiDung nguoiDung = nguoiDungRepository.findByUsername(authentication.getName()).orElse(null);
//
//        if (sanPham != null && nguoiDung != null) {
//            // 1. Chặn đánh giá trùng lặp
//            if (danhGiaRepository.existsBySanPham_IdAndNguoiDung_Id(id, nguoiDung.getId())) {
//                ra.addFlashAttribute("error", "Bạn đã gửi đánh giá cho sản phẩm này trước đó.");
//                return "redirect:/sanpham/" + id;
//            }
//
//            // 2. Gán dữ liệu
//            danhGia.setSanPham(sanPham);
//            danhGia.setNguoiDung(nguoiDung);
//            danhGia.setThoiGian(LocalDateTime.now());
//            danhGia.setDaDuyet(true); // Hiển thị ngay
//
//            // 3. Lưu
//            try {
//                danhGiaRepository.save(danhGia);
//                ra.addFlashAttribute("message", "Đánh giá của bạn đã được đăng thành công!");
//            } catch (Exception e) {
//                ra.addFlashAttribute("error", "Có lỗi xảy ra, vui lòng thử lại sau.");
//            }
//        }
//
//        return "redirect:/sanpham/" + id;
//    }
}