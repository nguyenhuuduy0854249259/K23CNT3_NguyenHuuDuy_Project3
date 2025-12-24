package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.service;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.LienHe;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.LienHeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LienHeService {

    private final LienHeRepository repo;

    /**
     * Lấy tất cả liên hệ
     */
    public List<LienHe> findAll() {
        return repo.findAll();
    }

    /**
     * Lưu hoặc cập nhật liên hệ
     * Nếu thời gian gửi chưa được set, sẽ set bằng thời gian hiện tại
     */
    public LienHe save(LienHe lh) {
        if (lh.getThoiGianGui() == null) {
            lh.setThoiGianGui(LocalDateTime.now());
        }
        return repo.save(lh);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    /**
     * Tìm liên hệ theo ID
     */
    public Optional<LienHe> findById(Long id) {
        return repo.findById(id);
    }

    /**
     * Xóa liên hệ theo ID
     */
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
