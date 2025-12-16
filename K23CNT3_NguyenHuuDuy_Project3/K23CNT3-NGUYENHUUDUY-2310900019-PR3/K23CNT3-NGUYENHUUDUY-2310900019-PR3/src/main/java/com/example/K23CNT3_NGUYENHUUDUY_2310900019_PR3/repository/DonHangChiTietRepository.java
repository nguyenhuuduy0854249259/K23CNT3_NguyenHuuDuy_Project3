package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.DonHangChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonHangChiTietRepository extends JpaRepository<DonHangChiTiet, Long> {

    List<DonHangChiTiet> findByDonHang_Id(Long donHangId);
}

