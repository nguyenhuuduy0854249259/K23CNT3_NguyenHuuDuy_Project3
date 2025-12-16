package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.SanPham;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.SanPhamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SanPhamService {

    private final SanPhamRepository sanPhamRepository;

    public List<SanPham> findAll() {
        return sanPhamRepository.findAll();
    }

    public Optional<SanPham> findById(Long id) {
        return sanPhamRepository.findById(id);
    }

    public List<SanPham> findByDanhMuc(Long danhMucId) {
        return sanPhamRepository.findByDanhMuc_Id(danhMucId);
    }

    public List<SanPham> findByThuongHieu(Long thId) {
        return sanPhamRepository.findByThuongHieu_Id(thId);
    }

    public SanPham save(SanPham sp) {
        return sanPhamRepository.save(sp);
    }

    public void delete(Long id) {
        sanPhamRepository.deleteById(id);
    }

    public long count() {
        return sanPhamRepository.count();
    }
}
