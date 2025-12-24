package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.DanhGia;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.DanhGiaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DanhGiaService {

    private final DanhGiaRepository danhGiaRepository;

    public List<DanhGia> findBySanPham(Long spId) {
        return danhGiaRepository.findBySanPham_Id(spId);
    }

    public DanhGia save(DanhGia dg) {
        return danhGiaRepository.save(dg);
    }

    public List<DanhGia> getAll() {
        return danhGiaRepository.findAllByOrderByThoiGianDesc();
    }

    public List<DanhGia> getChuaDuyet() {
        return danhGiaRepository.findByDaDuyetFalseOrderByThoiGianDesc();
    }

    public void duyet(Long id) {
        danhGiaRepository.findById(id).ifPresent(dg -> {
            dg.setDaDuyet(true);
            danhGiaRepository.save(dg);
        });
    }

    public void xoa(Long id) {
        danhGiaRepository.deleteById(id);
    }
}
