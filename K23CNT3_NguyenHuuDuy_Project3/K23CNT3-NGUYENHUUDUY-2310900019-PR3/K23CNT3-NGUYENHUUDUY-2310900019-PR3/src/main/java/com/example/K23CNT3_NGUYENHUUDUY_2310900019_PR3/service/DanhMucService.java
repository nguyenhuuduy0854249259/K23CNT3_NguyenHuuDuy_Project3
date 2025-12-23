package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.DanhMuc;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.DanhMucRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DanhMucService {

    private final DanhMucRepository danhMucRepository;

    // ===== LẤY DANH SÁCH =====
    public List<DanhMuc> findAll() {
        return danhMucRepository.findAll();
    }

    // ===== TÌM THEO ID =====
    public Optional<DanhMuc> findById(Long id) {
        return danhMucRepository.findById(id);
    }

    // ===== LƯU (FIX LỖI TRANSIENT OBJECT) =====
    @Transactional
    public DanhMuc save(DanhMuc dm) {

        // Nếu có danh mục cha
        if (dm.getParent() != null && dm.getParent().getId() != null) {

            DanhMuc parent = danhMucRepository
                    .findById(dm.getParent().getId())
                    .orElseThrow(() ->
                            new RuntimeException("Danh mục cha không tồn tại"));

            // Gán entity đã được quản lý (managed)
            dm.setParent(parent);

        } else {
            // Không có danh mục cha
            dm.setParent(null);
        }

        return danhMucRepository.save(dm);
    }

    // ===== XOÁ =====
    public void delete(Long id) {
        danhMucRepository.deleteById(id);
    }
}
