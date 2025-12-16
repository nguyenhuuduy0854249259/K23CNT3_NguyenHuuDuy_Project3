package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.DanhGia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DanhGiaRepository extends JpaRepository<DanhGia, Long> {
    List<DanhGia> findBySanPham_Id(Long sanPhamId);

    List<DanhGia> findBySanPham_IdOrderByThoiGianDesc(Long sanPhamId);
    List<DanhGia> findBySanPham_IdAndDaDuyetTrueOrderByThoiGianDesc(Long sanPhamId);

    boolean existsBySanPham_IdAndNguoiDung_Id(Long sanPhamId, Long nguoiDungId);

    List<DanhGia> findByDaDuyetFalse();
    // ⭐ TÍNH SAO TRUNG BÌNH
    // =========================
    @Query("""
        SELECT AVG(d.soSao)
        FROM DanhGia d
        WHERE d.sanPham.id = :sanPhamId
          AND d.daDuyet = true
    """)
    Double tinhSaoTrungBinh(@Param("sanPhamId") Long sanPhamId);
}
