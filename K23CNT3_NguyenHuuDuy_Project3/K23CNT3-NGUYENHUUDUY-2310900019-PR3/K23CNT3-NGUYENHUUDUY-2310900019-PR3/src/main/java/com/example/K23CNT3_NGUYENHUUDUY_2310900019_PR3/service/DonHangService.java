package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.DonHang;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.DonHangChiTiet;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.GioHangChiTiet;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.DonHangRepository;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.DonHangChiTietRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public DonHang save(DonHang dh) {
        return donHangRepository.save(dh);
    }

    public long count() {
        return donHangRepository.count();
    }

    // ==============================
    // CHUYỂN CHI TIẾT GIỎ HÀNG SANG ĐƠN HÀNG
    // ==============================
    public void addChiTietDonHang(DonHang donHang, GioHangChiTiet ct) {
        DonHangChiTiet dhct = DonHangChiTiet.builder()
                .donHang(donHang)
                .sanPham(ct.getSanPham())
                .soLuong(ct.getSoLuong())
                .gia(ct.getSanPham().getGia())
                .build();
        donHangChiTietRepository.save(dhct);

        // thêm vào danh sách chi tiết đơn hàng
        donHang.getChiTietDonHang().add(dhct);
    }
}
