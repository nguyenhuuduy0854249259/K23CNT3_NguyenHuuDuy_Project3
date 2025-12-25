package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.Controller.Admin;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.ThuongHieu;
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
@RequestMapping("/admin/thuonghieu")
@RequiredArgsConstructor
public class AdminThuongHieuController {

    private final ThuongHieuService thuongHieuService;

    @Value("${upload.dir}")
    private String uploadDir;

    @GetMapping({"", "/list"})
    public String list(Model model) {
        model.addAttribute("list", thuongHieuService.findAll());
        return "admin/thuonghieu/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("thuongHieu", new ThuongHieu());
        model.addAttribute("title", "Thêm thương hiệu");
        model.addAttribute("action", "/admin/thuonghieu/create");
        return "admin/thuonghieu/form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute ThuongHieu th, @RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            th.setLogo(handleFileUpload(file));
        }
        thuongHieuService.save(th);
        return "redirect:/admin/thuonghieu";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        ThuongHieu th = thuongHieuService.findById(id).orElseThrow();
        model.addAttribute("thuongHieu", th);
        model.addAttribute("title", "Sửa thương hiệu");
        model.addAttribute("action", "/admin/thuonghieu/edit/" + id);
        return "admin/thuonghieu/form";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id, @ModelAttribute ThuongHieu th, @RequestParam("file") MultipartFile file) {
        ThuongHieu oldTh = thuongHieuService.findById(id).orElseThrow();
        if (!file.isEmpty()) {
            th.setLogo(handleFileUpload(file));
        } else {
            th.setLogo(oldTh.getLogo());
        }
        th.setId(id);
        thuongHieuService.save(th);
        return "redirect:/admin/thuonghieu";
    }

    // Hàm helper xử lý upload (copy từ SanPhamController)
    private String handleFileUpload(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir);
            if (!Files.exists(path)) Files.createDirectories(path);
            Files.copy(file.getInputStream(), path.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) { return null; }
    }
}