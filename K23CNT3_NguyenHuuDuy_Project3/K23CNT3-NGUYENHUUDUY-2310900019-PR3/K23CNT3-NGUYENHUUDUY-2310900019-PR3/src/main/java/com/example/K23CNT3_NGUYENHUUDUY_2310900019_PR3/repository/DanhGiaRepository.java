package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.DanhGia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DanhGiaRepository extends JpaRepository<DanhGia, Long> {

    // Lấy danh sách đánh giá của 1 sản phẩm (Dùng cho Admin xem chi tiết)
    List<DanhGia> findBySanPham_IdOrderByThoiGianDesc(Long sanPhamId);

    // Lấy danh sách đánh giá đã duyệt (Dùng cho trang chủ/chi tiết sản phẩm)
    List<DanhGia> findBySanPham_IdAndDaDuyetTrueOrderByThoiGianDesc(Long sanPhamId);

    // Kiểm tra trùng lặp đánh giá
    boolean existsBySanPham_IdAndNguoiDung_Id(Long sanPhamId, Long nguoiDungId);

    List<DanhGia> findBySanPham_Id(Long sanPhamId);

    // Tính sao trung bình
    @Query("SELECT AVG(d.soSao) FROM DanhGia d WHERE d.sanPham.id = :sanPhamId AND d.daDuyet = true")
    Double tinhSaoTrungBinh(@Param("sanPhamId") Long sanPhamId);

    // Các hàm cho Admin
    List<DanhGia> findAllByOrderByThoiGianDesc();
    List<DanhGia> findByDaDuyetFalseOrderByThoiGianDesc();
    long countByDaDuyetFalse();
}