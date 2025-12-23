package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.TinTuc;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.TinTucRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TinTucService {

    private final TinTucRepository repo;

    /**
     * Lấy tất cả tin tức
     */
    public List<TinTuc> findAll() {
        return repo.findAll();
    }

    /**
     * Lưu hoặc cập nhật tin tức
     * Nếu thời gian chưa được set, sẽ set bằng thời gian hiện tại
     */
    public TinTuc save(TinTuc t) {
        if (t.getThoiGian() == null) {
            t.setThoiGian(LocalDateTime.now());
        }
        return repo.save(t);
    }

    /**
     * Tìm tin tức theo ID
     */
    public Optional<TinTuc> findById(Long id) {
        return repo.findById(id);
    }

    /**
     * Xóa tin tức theo ID
     */
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
