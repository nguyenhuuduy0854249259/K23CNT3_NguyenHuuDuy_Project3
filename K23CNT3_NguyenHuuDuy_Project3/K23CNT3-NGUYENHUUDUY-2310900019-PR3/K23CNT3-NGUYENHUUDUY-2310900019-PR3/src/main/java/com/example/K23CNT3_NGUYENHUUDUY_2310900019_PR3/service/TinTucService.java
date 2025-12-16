package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.TinTuc;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.TinTucRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TinTucService {

    private final TinTucRepository repo;

    public List<TinTuc> findAll() {
        return repo.findAll();
    }

    public TinTuc save(TinTuc t) {
        return repo.save(t);
    }

    public Optional<TinTuc> findById(Long id) {
        return repo.findById(id);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
