package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.LienHe;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.LienHeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LienHeService {

    private final LienHeRepository repo;

    public List<LienHe> findAll() {
        return repo.findAll();
    }

    public LienHe save(LienHe lh) {
        return repo.save(lh);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
