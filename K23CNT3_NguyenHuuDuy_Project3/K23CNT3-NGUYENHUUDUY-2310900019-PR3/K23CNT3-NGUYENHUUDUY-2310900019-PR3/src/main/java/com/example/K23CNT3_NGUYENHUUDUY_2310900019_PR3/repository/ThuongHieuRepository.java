package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository;


import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.ThuongHieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThuongHieuRepository extends JpaRepository<ThuongHieu, Long> {
}
