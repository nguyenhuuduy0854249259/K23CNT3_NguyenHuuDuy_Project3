package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.DonHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonHangRepository extends JpaRepository<DonHang, Long> {

    List<DonHang> findByNguoiDung_Id(Long nguoiDungId);

    Optional<DonHang> findByMaDonHang(String maDonHang);
}
