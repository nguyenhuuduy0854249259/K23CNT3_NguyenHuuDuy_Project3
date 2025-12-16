package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.ThuongHieu;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.ThuongHieuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ThuongHieuService {

    private final ThuongHieuRepository thuongHieuRepository;

    public List<ThuongHieu> findAll() {
        return thuongHieuRepository.findAll();
    }

    public Optional<ThuongHieu> findById(Long id) {
        return thuongHieuRepository.findById(id);
    }

    public ThuongHieu save(ThuongHieu th) {
        return thuongHieuRepository.save(th);
    }

    public void delete(Long id) {
        thuongHieuRepository.deleteById(id);
    }
}

