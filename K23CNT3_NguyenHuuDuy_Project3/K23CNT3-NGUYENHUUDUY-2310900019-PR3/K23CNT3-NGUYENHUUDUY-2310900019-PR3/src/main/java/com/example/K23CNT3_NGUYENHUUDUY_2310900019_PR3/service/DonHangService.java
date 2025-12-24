package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.DonHang;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.DonHangChiTiet;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.GioHangChiTiet;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.DonHangRepository;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.DonHangChiTietRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DonHangService {

    private final DonHangRepository donHangRepository;
    private final DonHangChiTietRepository donHangChiTietRepository;

    public List<DonHang> findByNguoiDung(Long userId) {
        return donHangRepository.findByNguoiDungId(userId);
    }

    public Optional<DonHang> findByMaDonHang(String ma) {
        return donHangRepository.findByMaDonHang(ma);
    }

    public Optional<DonHang> findById(Long id) {
        return donHangRepository.findById(id);
    }

    public DonHang save(DonHang dh) {
        return donHangRepository.save(dh);
    }

    public long count() {
        return donHangRepository.count();
    }

    // Thêm vào trong class DonHangService
    public List<DonHang> findAll() {
        return donHangRepository.findAll();
    }

    // ==========================================
    // PHƯƠNG THỨC TẠO ĐƠN HÀNG HOÀN CHỈNH
    // ==========================================
    @Transactional
    public DonHang createDonHang(DonHang donHang, List<GioHangChiTiet> cartItems) {
        // 1. Kiểm tra và khởi tạo list nếu bị null để tránh lỗi .getDonHangChiTiet()
        if (donHang.getDonHangChiTiet() == null) {
            donHang.setDonHangChiTiet(new ArrayList<>());
        }

        // 2. Lưu DonHang trước để có ID (bắt buộc để làm khóa ngoại)
        DonHang savedDonHang = donHangRepository.save(donHang);

        // 3. Chuyển từ GioHangChiTiet sang DonHangChiTiet
        for (GioHangChiTiet item : cartItems) {
            DonHangChiTiet dhct = DonHangChiTiet.builder()
                    .donHang(savedDonHang) // Quan trọng: Gán đơn hàng đã lưu vào đây
                    .sanPham(item.getSanPham())
                    .soLuong(item.getSoLuong())
                    .gia(item.getSanPham().getGia())
                    .build();

            // Lưu từng chi tiết vào DB
            donHangChiTietRepository.save(dhct);

            // Thêm vào list của object để trả về đầy đủ dữ liệu
            savedDonHang.getDonHangChiTiet().add(dhct);
        }

        return savedDonHang;
    }

    // Nếu bạn vẫn muốn dùng phương thức cũ, hãy sửa như sau:
    @Transactional
    public void addChiTietDonHang(DonHang donHang, GioHangChiTiet ct) {
        // Kiểm tra null an toàn
        if (donHang.getDonHangChiTiet() == null) {
            donHang.setDonHangChiTiet(new ArrayList<>());
        }

        DonHangChiTiet dhct = DonHangChiTiet.builder()
                .donHang(donHang)
                .sanPham(ct.getSanPham())
                .soLuong(ct.getSoLuong())
                .gia(ct.getSanPham().getGia())
                .build();

        donHangChiTietRepository.save(dhct);
        donHang.getDonHangChiTiet().add(dhct);
    }
}