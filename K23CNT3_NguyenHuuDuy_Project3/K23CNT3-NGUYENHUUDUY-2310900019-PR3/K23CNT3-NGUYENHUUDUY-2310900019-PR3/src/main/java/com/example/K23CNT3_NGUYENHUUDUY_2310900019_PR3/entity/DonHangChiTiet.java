package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "don_hang_chi_tiet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DonHangChiTiet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "don_hang_id")
    private DonHang donHang;

    @ManyToOne
    @JoinColumn(name = "san_pham_id")
    private SanPham sanPham;

    private int soLuong;
    private double gia;
}

