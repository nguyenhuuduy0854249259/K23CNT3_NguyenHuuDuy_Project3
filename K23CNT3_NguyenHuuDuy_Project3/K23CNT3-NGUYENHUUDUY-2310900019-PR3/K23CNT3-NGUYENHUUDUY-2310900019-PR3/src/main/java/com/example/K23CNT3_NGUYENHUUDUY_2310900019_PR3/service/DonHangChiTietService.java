package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.DonHangChiTiet;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.DonHangChiTietRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DonHangChiTietService {

    private final DonHangChiTietRepository repo;

    public List<DonHangChiTiet> findByDonHang(Long dhId) {
        return repo.findByDonHang_Id(dhId);
    }

    public DonHangChiTiet save(DonHangChiTiet ct) {
        return repo.save(ct);
    }
}
