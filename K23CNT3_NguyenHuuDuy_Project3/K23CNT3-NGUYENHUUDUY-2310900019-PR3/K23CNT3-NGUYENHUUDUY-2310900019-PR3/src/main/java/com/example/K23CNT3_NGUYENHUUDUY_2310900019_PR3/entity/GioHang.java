package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "gio_hang")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GioHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Giả sử bạn có bảng NguoiDung, khóa ngoại
    @Column(name = "nguoi_dung_id", nullable = false)
    private Long nguoiDungId;

    @OneToMany(mappedBy = "gioHang", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GioHangChiTiet> chiTietList = new ArrayList<>();

    public GioHangChiTiet findChiTiet(Long productId) {
        return chiTietList.stream()
                .filter(ct -> ct.getSanPham().getId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    public void removeChiTiet(Long productId) {
        chiTietList.removeIf(ct -> ct.getSanPham().getId().equals(productId));
    }
}
