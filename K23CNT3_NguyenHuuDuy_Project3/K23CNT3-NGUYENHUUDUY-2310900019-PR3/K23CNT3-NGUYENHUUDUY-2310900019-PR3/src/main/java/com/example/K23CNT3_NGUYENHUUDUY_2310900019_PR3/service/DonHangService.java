package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.DonHang;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.DonHangRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DonHangService {

    private final DonHangRepository donHangRepository;

    public List<DonHang> findByNguoiDung(Long userId) {
        return donHangRepository.findByNguoiDung_Id(userId);
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
}
