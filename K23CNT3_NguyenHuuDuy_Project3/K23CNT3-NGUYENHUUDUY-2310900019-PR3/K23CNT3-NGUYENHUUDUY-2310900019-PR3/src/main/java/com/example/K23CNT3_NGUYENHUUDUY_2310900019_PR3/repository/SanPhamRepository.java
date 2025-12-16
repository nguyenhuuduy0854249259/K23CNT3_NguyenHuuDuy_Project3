package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository;


import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Long> {

    List<SanPham> findByDanhMuc_Ten(String tenDanhMuc);

    List<SanPham> findByDanhMuc_Id(Long danhMucId);

    List<SanPham> findByThuongHieu_Id(Long thuongHieuId);

    List<SanPham> findByTenContainingIgnoreCase(String ten);

    List<SanPham> findByDanhMuc_Parent_Id(Long parentId);
}


