package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.Controller.Admin;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.SanPham;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service.DanhMucService;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service.SanPhamService;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service.ThuongHieuService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Controller
@RequestMapping("/admin/sanpham")
@RequiredArgsConstructor
public class AdminSanPhamController {

    private final SanPhamService sanPhamService;
    private final DanhMucService danhMucService;
    private final ThuongHieuService thuongHieuService;

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
}