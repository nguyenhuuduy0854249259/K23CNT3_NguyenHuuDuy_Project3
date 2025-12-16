package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.VaiTro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VaiTroRepository extends JpaRepository<VaiTro, Long> {
    Optional<VaiTro> findByTen(String ten);
}
