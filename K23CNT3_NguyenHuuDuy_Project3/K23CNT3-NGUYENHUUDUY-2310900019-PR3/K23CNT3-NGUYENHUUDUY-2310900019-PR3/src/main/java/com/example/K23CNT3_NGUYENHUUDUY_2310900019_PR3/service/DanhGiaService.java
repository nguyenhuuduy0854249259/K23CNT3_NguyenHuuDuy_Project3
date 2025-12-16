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
}
