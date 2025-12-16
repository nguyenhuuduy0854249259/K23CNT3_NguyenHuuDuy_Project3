package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.DanhMuc;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.DanhMucRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DanhMucService {

    private final DanhMucRepository danhMucRepository;

    public List<DanhMuc> findAll() {
        return danhMucRepository.findAll();
    }

    public Optional<DanhMuc> findById(Long id) {
        return danhMucRepository.findById(id);
    }

    public DanhMuc save(DanhMuc danhMuc) {
        return danhMucRepository.save(danhMuc);
    }

    public void delete(Long id) {
        danhMucRepository.deleteById(id);
    }
}


