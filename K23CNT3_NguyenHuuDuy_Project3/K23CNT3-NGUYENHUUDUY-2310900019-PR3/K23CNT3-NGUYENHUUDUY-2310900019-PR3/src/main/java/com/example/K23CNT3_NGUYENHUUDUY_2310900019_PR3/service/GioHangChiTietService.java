package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.GioHangChiTiet;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.GioHangChiTietRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GioHangChiTietService {

    private final GioHangChiTietRepository repo;

    public Optional<GioHangChiTiet> findByGioHangAndSanPham(Long ghId, Long spId) {
        return repo.findByGioHang_IdAndSanPham_Id(ghId, spId);
    }

    public GioHangChiTiet save(GioHangChiTiet ct) {
        return repo.save(ct);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
