package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.VaiTro;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.VaiTroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VaiTroService {

    private final VaiTroRepository vaiTroRepository;

    public List<VaiTro> findAll() {
        return vaiTroRepository.findAll();
    }

    public VaiTro save(VaiTro vaiTro) {
        return vaiTroRepository.save(vaiTro);
    }

    public Optional<VaiTro> findById(Long id) {
        return vaiTroRepository.findById(id);
    }

    public void delete(Long id) {
        vaiTroRepository.deleteById(id);
    }
}

