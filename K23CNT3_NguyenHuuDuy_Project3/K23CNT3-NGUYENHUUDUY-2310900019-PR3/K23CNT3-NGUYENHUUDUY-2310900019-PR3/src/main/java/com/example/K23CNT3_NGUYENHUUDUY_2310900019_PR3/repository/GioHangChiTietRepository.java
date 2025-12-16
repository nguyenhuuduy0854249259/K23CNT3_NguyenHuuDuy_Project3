package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.GioHangChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GioHangChiTietRepository extends JpaRepository<GioHangChiTiet, Long> {

    Optional<GioHangChiTiet> findByGioHang_IdAndSanPham_Id(Long gioHangId, Long sanPhamId);
}

